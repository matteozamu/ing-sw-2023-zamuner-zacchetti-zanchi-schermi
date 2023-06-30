package it.polimi.ingsw.network.server;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleGameState;
import it.polimi.ingsw.enumeration.UserPlayerState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utility.JsonReader;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * This class is the main server class which starts a Socket and an RMI server.
 * It handles all the client regardless of whether they are Sockets or RMI
 */
public class Server implements Runnable {
    public static final Logger LOGGER = Logger.getLogger("Server");
    private final Object clientsLock = new Object();
    private int socketPort;
    private int RMIPort;
    private Map<String, Connection> clients;
    private Map<String, ControllerGame> playersGame;
    private List<ControllerGame> controllerGames;
    private boolean waitForLoad;
    private String filepath;

    /**
     * Constructor of a new game
     * @param confFilePath the path of the configuration file
     */
    public Server(String confFilePath) {
        initLogger();
        synchronized (clientsLock) {
            clients = new HashMap<>();
        }
        this.waitForLoad = false;
        JsonReader.readJsonConstant(confFilePath);
        this.filepath = confFilePath;
        this.socketPort = JsonReader.getSocketPort();
        this.RMIPort = JsonReader.getRMIPort();

        startServers();

        controllerGames = new ArrayList<>();
        this.playersGame = new HashMap<>();

        Thread pingThread = new Thread(this);
        pingThread.start();
    }

    /**
     * Starts the server.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0)
            new Server("GameConstant.json");
        else new Server(args[0]);
    }

    /**
     * Initialize the logger
     */
    private void initLogger() {
        Date date = GregorianCalendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM_HH.mm.ss");

        try {
            FileHandler fh = new FileHandler("log/server-" + dateFormat.format(date) + ".log");
            fh.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Starts the RMI server and the socket server
     */
    private void startServers() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startServer();

        LOGGER.info("Socket Server Started");

        RMIServer rmiServer = new RMIServer(this, RMIPort);
        rmiServer.startServer();

        LOGGER.info("RMI Server Started");
    }

    /**
     * Adds or reconnects a player to the server
     *
     * @param username   username of the player
     * @param connection connection of the client
     */
    void login(String username, Connection connection) {
        try {
            synchronized (clientsLock) {
                if (clients.containsKey(username)) {
                    knownPlayerLogin(username, connection);
                } else {
                    newPlayerLogin(username, connection);
                }
            }
        } catch (IOException e) {
            connection.disconnect();
        }
    }

    /**
     * Handles a known player login
     *
     * @param username   username of the player who is trying to login
     * @param connection connection of the client
     * @throws IOException when send message fails
     */
    private void knownPlayerLogin(String username, Connection connection) throws IOException {
        ControllerGame controllerGame = playersGame.get(username);
        if (clients.get(username) == null || !clients.get(username).isConnected()) { // Player Reconnection
            clients.replace(username, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            if (waitForLoad) {// Game in lobby state for load a game
                connection.sendMessage(
                        new GameLoadResponse("Successfully reconnected", token, UserPlayerState.FIRST_ACTION)
                );
            } else {
                if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM) { // Game in lobby state
                    connection.sendMessage(
                            new ConnectionResponse("Successfully reconnected", token, MessageStatus.OK)
                    );
                } else { // Game has already been started
                    connection.sendMessage(
                            controllerGame.onConnectionMessage(new LobbyMessage(username, token, false))
                    );
                }
            }

            LOGGER.log(Level.INFO, "{0} reconnected to server!", username);
        } else { // Player already connected
            connection.sendMessage(
                    new ConnectionResponse("Player already connected", null, MessageStatus.ERROR)
            );

            connection.disconnect();
            LOGGER.log(Level.INFO, "{0} already connected to server!", username);
        }
    }

    /**
     * Method called when a new player logged in the game
     *
     * @param username   the name chosen by the user
     * @param connection the client connection
     * @throws IOException if the client can't log in successfully
     */
    private void newPlayerLogin(String username, Connection connection) throws IOException {
        clients.put(username, connection);
        String token = UUID.randomUUID().toString();
        connection.setToken(token);
        connection.sendMessage(new ConnectionResponse("Successfully connected", token, MessageStatus.OK));
        LOGGER.log(Level.INFO, "{0} connected to server!", username);
    }

    /**
     * Handles a received message
     * @param message is the message received to the client
     */
    void onMessage(Message message) {
        if (message != null && message.getSenderUsername() != null && (message.getToken() != null || message.getSenderUsername().equals("serverUser"))) {
            LOGGER.log(Level.INFO, "Received: {0}", message);

            String msgToken = message.getToken();
            Connection conn;

            synchronized (clientsLock) {
                conn = clients.get(message.getSenderUsername());
            }

            if (conn == null) {
                LOGGER.log(Level.INFO, "Message Request {0} - Unknown username {1}", new Object[]{message.getContent().name(), message.getSenderUsername()});
            } else if (msgToken.equals(conn.getToken())) {
                if (message.getContent() == MessageContent.LIST_GAME) {
                    List<ControllerGame> gameAvailable = new ArrayList<>();
                    for (ControllerGame cg : controllerGames) {
                        if (cg.getGameState() == PossibleGameState.GAME_ROOM && !cg.getIsLobbyFull() && cg.getGame().getNumberOfPlayers() != -1) {
                            gameAvailable.add(cg);
                        }
                    }
                    sendMessage(message.getSenderUsername(), new ListGameResponse(gameAvailable));
                } else if (message.getContent() == MessageContent.JOIN_GAME) {
                    UUID gameUUID = ((JoinGameRequest) message).getGameUUID();
                    ControllerGame controllerGame = findGameByUUID(gameUUID);

                    if (controllerGame == null) {
                        sendMessage(message.getSenderUsername(), new Response("Game not found", MessageStatus.ERROR));
                        return;
                    }
                    this.playersGame.put(message.getSenderUsername(), controllerGame);
                    sendMessage(message.getSenderUsername(), new Response("Game joined", MessageStatus.GAME_JOINED));
                } else if (message.getContent() == MessageContent.CREATE_GAME) {
                    LOGGER.log(Level.INFO, "PLAYER " + message.getSenderUsername(), "");
                    LOGGER.log(Level.INFO, "CREATE GAME", "");
                    ControllerGame controllerGame = new ControllerGame(this);
                    this.controllerGames.add(controllerGame);
                    this.playersGame.put(message.getSenderUsername(), controllerGame);
                    sendMessage(message.getSenderUsername(), new Response("Game created", MessageStatus.GAME_CREATED));
                    LOGGER.log(Level.INFO, "PLAYER " + message.getSenderUsername(), "");
                    LOGGER.log(Level.INFO, "CONTROLLER GAME: " + controllerGame.getId(), "");
                } else {
                    LOGGER.log(Level.INFO, "PLAYER " + message.getSenderUsername(), "");
                    LOGGER.log(Level.INFO, "CONTROLLER GAME " + this.playersGame.get(message.getSenderUsername()).getId(), "");
                    ControllerGame controllerGame = this.playersGame.get(message.getSenderUsername());
                    Message response = controllerGame.onMessage(message);
                    sendMessage(message.getSenderUsername(), response);

                    if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM && controllerGame.getIsLobbyFull())
                        controllerGame.gameSetupHandler();
                }
            }
        }
    }

    /**
     * Method used to disconnect a client from the server
     *
     * @param playerConnection the connection of the player to delete
     */
    void onDisconnect(Connection playerConnection) {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            LOGGER.log(Level.INFO, "{0} disconnected from server!", username);

            if (playersGame.get(username) != null) {
                ControllerGame controllerGame = findGameByPlayerUsername(username);
                Player p = controllerGame.getGame().getPlayerByName(username);
                p.setConnected(false);

                int connectionCounter = 0;
                for (Player pl : controllerGame.getGame().getPlayers()) {
                    if (pl.isConnected()) connectionCounter++;
                }
                if (connectionCounter == 1) {
                    controllerGame.setTimer();
                }

                if (username.equals(controllerGame.getGame().getCurrentPlayer().getName())) {
                    controllerGame.getGame().nextPlayer();

                    List<Player> players = controllerGame.getGame().getPlayers();

                    for (Player player : players) {
                        sendMessage(player.getName(), new GameStateResponse(player.getName(), controllerGame.getGame().getCurrentPlayer().getName(), getFilepath()));
                    }
                }

                LOGGER.log(Level.INFO, "{0} set connected false!", username);

                if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM) {
                    synchronized (clientsLock) {
                        clients.remove(username);
                        playersGame.remove(username);
                        controllerGame.getGame().getPlayers().remove(p);
                    }
                    sendMessageToAll(controllerGame.getId(), new LobbyPlayersResponse(new ArrayList<>(controllerGame.getGame().getPlayers().stream().map(Player::getName).collect(Collectors.toList()))));
                    LOGGER.log(Level.INFO, "{0} removed from client list!", username);
                }
                sendMessageToAll(controllerGame.getId(), new DisconnectionMessage(username));
            } else {
                synchronized (clientsLock) {
                    clients.remove(username);
                }
            }
        }
    }

    /**
     * Method that send a message to all the players present in one game
     *
     * @param gameId  is the unique id of a single game
     * @param message is the message to send
     */
    public void sendMessageToAll(UUID gameId, Message message) {
        for (Map.Entry<String, ControllerGame> client : playersGame.entrySet()) {
            synchronized (clientsLock) {
                if (client.getValue() != null && client.getValue().getId().equals(gameId) && clients.get(client.getKey()).isConnected()) {
                    try {
                        clients.get(client.getKey()).sendMessage(message);
                    } catch (IOException e) {
                        LOGGER.severe(e.getMessage());
                    }
                }
            }
        }
        LOGGER.log(Level.INFO, "Send to all: {0}", message);
    }

    /**
     * Send a message to a single client
     *
     * @param username is the name of the player to send the message to
     * @param message  is the message to send
     */
    public void sendMessage(String username, Message message) {
        synchronized (clientsLock) {
            for (Map.Entry<String, Connection> client : clients.entrySet()) {
                if (client.getKey().equals(username) && client.getValue() != null && client.getValue().isConnected()) {
                    try {
                        client.getValue().sendMessage(message);
                    } catch (IOException e) {
                        LOGGER.severe(e.getMessage());
                    }
                    break;
                }
            }
        }

        LOGGER.log(Level.INFO, "Send: {0}, {1}", new Object[]{message.getSenderUsername(), message});
    }

    /**
     * Method that return the username given a connection
     *
     * @param connection is the client connection
     * @return the username
     */
    private String getUsernameByConnection(Connection connection) {
        Set<String> usernameList;
        synchronized (clientsLock) {
            usernameList = clients.entrySet()
                    .stream()
                    .filter(entry -> connection.equals(entry.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
        }
        if (usernameList.isEmpty()) {
            return null;
        } else {
            return usernameList.iterator().next();
        }
    }


    /**
     * Run the ping to check if client is still connected
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (clientsLock) {
                for (Map.Entry<String, Connection> client : clients.entrySet()) {
                    if (client.getValue() != null && client.getValue().isConnected()) {
                        client.getValue().ping();
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.severe(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method that return a controller game given the game UUID
     *
     * @param gameUUID is the game UUID
     * @return the controller game of the game
     */
    private ControllerGame findGameByUUID(UUID gameUUID) {
        for (ControllerGame cg : controllerGames) {
            if (cg.getId().equals(gameUUID)) {
                return cg;
            }
        }
        return null;
    }

    /**
     * Method that return a controller game given a username
     *
     * @param username is the name of the user
     * @return the controller game of the game in which the user is present
     */
    private ControllerGame findGameByPlayerUsername(String username) {
        return playersGame.get(username);
    }

    /**
     * Returns the players connected to the fame
     * @return a map with all the player connected to the server and the controller game of the game in which they are
     */
    public Map<String, ControllerGame> getPlayersGame() {
        return playersGame;
    }

    /**
     * Returns the lock of the clients map
     * @return the lock of the clients map
     */
    public Object getClientsLock() {
        return clientsLock;
    }

    /**
     * Returns the clients with their connection
     * @return a map with all the clients connected and their connection
     */
    public Map<String, Connection> getClients() {
        return clients;
    }

    /**
     * Returns the list of all the controller games
     * @return the list of all the controller games
     */
    public List<ControllerGame> getControllerGames() {
        return controllerGames;
    }

    /**
     * Returns the filepath of the server
     * @return the filepath of the server
     */
    public String getFilepath() {
        return filepath;
    }
}