package it.polimi.ingsw.network.server;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleGameState;
import it.polimi.ingsw.enumeration.UserPlayerState;
import it.polimi.ingsw.network.message.*;

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
 * This class is the main server class which starts a Socket and a RMI server.
 * It handles all the client regardless of whether they are Sockets or RMI
 */
public class Server implements Runnable {
    public static final Logger LOGGER = Logger.getLogger("Server");
    private final Object clientsLock = new Object();
    private int socketPort = 2727;
    private int rmiPort = 7272;
    private Map<String, Connection> clients;
    private Map<String, ControllerGame> playersGame;
    private List<ControllerGame> controllerGames;
    private boolean waitForLoad;

    private Timer moveTimer;

    /**
     * costructor of a new game
     */
    public Server() {
        initLogger();
        synchronized (clientsLock) {
            clients = new HashMap<>();
        }
        this.waitForLoad = false;

        startServers();

        controllerGames = new ArrayList<>();
        this.playersGame = new HashMap<>();

        Thread pingThread = new Thread(this);
        pingThread.start();

        moveTimer = new Timer();
    }


//    /**
//     * Starts the server loading a game
//     *
//     * @param confFilePath path of the config file
//     */
//    private Server(String confFilePath) {
//        initLogger();
//        synchronized (clientsLock) {
//            this.clients = new HashMap<>();
//        }
//        this.waitForLoad = true;
//
//        loadConfigFile(confFilePath);
//
//        startServers();
//
//        this.gameManager = SaveGame.loadGame(this, startTime);
//        reserveSlots(gameManager.getGameInstance().getPlayers());
//
//        LOGGER.log(Level.INFO, "Game loaded successfully.");
//
//        Thread pingThread = new Thread(this);
//        pingThread.start();
//
//        moveTimer = new Timer();
//    }

    public static void main(String[] args) {
        new Server();
    }

    /**
     * initialize the logger
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
     * starts the RMI server and the socket server
     */
    private void startServers() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startServer();

        LOGGER.info("Socket Server Started");

        RMIServer rmiServer = new RMIServer(this, rmiPort);
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
        if (clients.get(username) == null || !clients.get(username).isConnected()) { // Player Reconnection
            clients.replace(username, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

//            if (waitForLoad) {// Game in lobby state for load a game
            connection.sendMessage(
                    new GameLoadResponse("Successfully reconnected", token, UserPlayerState.FIRST_ACTION)
            );
//                checkLoadReady();
//            } else {
//                if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM) { // Game in lobby state
//                    connection.sendMessage(
//                            new ConnectionResponse("Successfully reconnected", token, MessageStatus.OK)
//                    );
//                } else { // Game started
//                    connection.sendMessage(
//                            controllerGame.onConnectionMessage(new LobbyMessage(username, token, null, false))
//                    );
//                }
//            }

            LOGGER.log(Level.INFO, "{0} reconnected to server!", username);
        } else { // Player already connected
            connection.sendMessage(
                    new ConnectionResponse("Player already connected", null, MessageStatus.ERROR)
            );

            connection.disconnect();
            LOGGER.log(Level.INFO, "{0} already connected to server!", username);
        }
    }

    private void newPlayerLogin(String username, Connection connection) throws IOException {
        clients.put(username, connection);
        String token = UUID.randomUUID().toString();
        connection.setToken(token);
        connection.sendMessage(new ConnectionResponse("Successfully connected", token, MessageStatus.OK));
        LOGGER.log(Level.INFO, "{0} connected to server!", username);
    }

    /**
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
                    ControllerGame controllerGame = null;
                    for (ControllerGame cg : controllerGames) {
                        if (cg.getId().equals(gameUUID)) {
                            controllerGame = cg;
                            break;
                        }
                    }
                    if (controllerGame == null) {
                        sendMessage(message.getSenderUsername(), new Response("Game not found", MessageStatus.ERROR));
                        return;
                    }
                    this.playersGame.put(message.getSenderUsername(), controllerGame);
                    sendMessage(message.getSenderUsername(), new Response("Game joined", MessageStatus.GAME_JOINED));
                } else if (message.getContent() == MessageContent.CREATE_GAME) {
                    System.out.println("CREATE GAME");
                    ControllerGame controllerGame = new ControllerGame(this);
                    this.controllerGames.add(controllerGame);
                    this.playersGame.put(message.getSenderUsername(), controllerGame);
                    sendMessage(message.getSenderUsername(), new Response("Game created", MessageStatus.GAME_CREATED));
                    System.out.println(controllerGames);
                    System.out.println(playersGame);
                } else {
                    ControllerGame controllerGame = this.playersGame.get(message.getSenderUsername());
                    Message response = controllerGame.onMessage(message);
                    sendMessage(message.getSenderUsername(), response);

                    if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM && controllerGame.getIsLobbyFull())
                        controllerGame.gameSetupHandler();
                }
            }
        }
    }

    void onDisconnect(Connection playerConnection) {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            LOGGER.log(Level.INFO, "{0} disconnected from server!", username);

//            if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM) {
//                synchronized (clientsLock) {
//                    clients.remove(username);
//                }
//                controllerGame.onMessage(new LobbyMessage(username, null, true));
//            LOGGER.log(Level.INFO, "{0} removed from client list!", username);
//            }
//        else {
//                controllerGame.onConnectionMessage(new LobbyMessage(username, null, true));
            sendMessageToAll(new DisconnectionMessage(username));
//            }
        }
    }

    public void sendMessageToAll(Message message) {
        for (Map.Entry<String, Connection> client : clients.entrySet()) {
            if (client.getValue() != null && client.getValue().isConnected()) {
                try {
                    client.getValue().sendMessage(message);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
            }
        }
        LOGGER.log(Level.INFO, "Send to all: {0}", message);
    }

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
}
