package it.polimi.ingsw.network.server;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utility.GameConstants;
import it.polimi.ingsw.utility.MoveTimer;

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
    private static final String DEFAULT_CONF_FILE_PATH = "conf.json";
    private final Object clientsLock = new Object();
    private int socketPort;
    private int rmiPort;
    private Map<String, Connection> clients;
    private ControllerGame controllerGame;
    private boolean waitForLoad;
    private int startTime;
    private int moveTime;

    private Timer moveTimer;

    /**
     * Starts the server loading a game
     *
     * @param confFilePath path of the config file
     */
    private Server(String confFilePath) {
        initLogger();
        synchronized (clientsLock) {
            this.clients = new HashMap<>();
        }
        this.waitForLoad = true;

        startServers();

        reserveSlots(controllerGame.getGame().getPlayers());

        LOGGER.log(Level.INFO, "Game loaded successfully.");

        Thread pingThread = new Thread(this);
        pingThread.start();

        moveTimer = new Timer();
    }

    /**
     * Starts the server with a new game
     *
     * @param bot          {@code true} if the bot is present, {@code false} otherwise
     * @param skullNum     number of skull
     * @param confFilePath path of the config file
     */
    public Server(boolean bot, int skullNum, String confFilePath) {
        initLogger();
        synchronized (clientsLock) {
            clients = new HashMap<>();
        }
        waitForLoad = false;

        startServers();

        controllerGame = new ControllerGame(this, bot, skullNum, startTime);

        Thread pingThread = new Thread(this);
        pingThread.start();

        moveTimer = new Timer();
    }

    public static void main(String[] args) {
        String confFilePath = DEFAULT_CONF_FILE_PATH;
        boolean terminator = false;
        int skullNum = 5;
        boolean reloadGame = false;

        // normal complete Server launch should have the following parameters: -l "confFilePath.txt" -b true/false -s #skulls
        // normal complete Server launch with game Reload should have the following parameter: -l "confFilePath.txt" -r

        if (args.length > 0 && args.length < 7) {
            int i = 0;
            while (i < args.length) {
                if (args[i].charAt(0) == '-' && args[i].length() == 2 && args.length >= i + 1) {
                    switch (args[i].charAt(1)) {
                        case 'l':
                            confFilePath = args[i + 1];
                            ++i;
                            break;
                        case 'b':
                            terminator = Boolean.parseBoolean(args[i + 1]);
                            ++i;
                            break;
                        case 's':
                            skullNum = Integer.parseInt(args[i + 1]);
                            ++i;
                            break;
                        case 'r':
                            reloadGame = true;
                            break;
                        default:
                            break;
                    }
                }

                ++i;
            }
        }

        // if the starting command contains -r it means that a game is going to be reloaded
        if (reloadGame) {
            new Server(confFilePath);
            return;
        }

        // if the passed value is correct it is used for the game, if not DEFAULT value is set back to 5
        if (skullNum < 5 || skullNum > 8) {
            skullNum = 5;
        }

        new Server(terminator, skullNum, confFilePath);
    }

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

    private void startServers() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startServer();

        LOGGER.info("Socket Server Started");

        RMIServer rmiServer = new RMIServer(this, rmiPort);
        rmiServer.startServer();

        LOGGER.info("RMI Server Started");
    }

    /**
     * Reserves server slots for player loaded from the game save
     *
     * @param loadedPlayers from the game save
     */
    private void reserveSlots(List<Player> loadedPlayers) {
        synchronized (clientsLock) {
            for (Player player : loadedPlayers) {
                clients.put(player.getName(), null);
            }
        }
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

            if (waitForLoad) {// Game in lobby state for load a game
                connection.sendMessage(
                        new GameLoadResponse("Successfully reconnected", token,
                                controllerGame.getUserPlayerState(username), controllerGame.getGame().isBotPresent())
                );
                checkLoadReady();
            } else {
                if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM) { // Game in lobby state
                    connection.sendMessage(
                            new ConnectionResponse("Successfully reconnected", token, MessageStatus.OK)
                    );
                } else { // Game started
                    connection.sendMessage(
                            controllerGame.onConnectionMessage(new LobbyMessage(username, token, null, false))
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
     * Handles a new player login
     *
     * @param username   username of the player who is trying to login
     * @param connection connection of the client
     * @throws IOException when send message fails
     */
    private void newPlayerLogin(String username, Connection connection) throws IOException {
        if (controllerGame.getGame().isGameStarted()) { // Game Started
            connection.sendMessage(
                    new ConnectionResponse("Game is already started!", null, MessageStatus.ERROR)
            );

            connection.disconnect();
            LOGGER.log(Level.INFO, "{0} attempted to connect!", username);
        } else if (controllerGame.isLobbyFull()) { // Lobby Full
            connection.sendMessage(
                    new ConnectionResponse("Max number of player reached", null, MessageStatus.ERROR)
            );

            connection.disconnect();
            LOGGER.log(Level.INFO, "{0} tried to connect but game is full!", username);
        } else { // New player
            if (isUsernameLegit(username)) { // Username legit
                clients.put(username, connection);

                String token = UUID.randomUUID().toString();
                connection.setToken(token);

                connection.sendMessage(
                        new ConnectionResponse("Successfully connected", token, MessageStatus.OK)
                );

                LOGGER.log(Level.INFO, "{0} connected to server!", username);
            } else { // Username not legit
                connection.sendMessage(
                        new ConnectionResponse("Invalid Username", null, MessageStatus.ERROR)
                );

                connection.disconnect();
                LOGGER.log(Level.INFO, "{0} tried to connect with invalid name!", username);
            }
        }
    }

    /**
     * Checks if all player of the loaded game have joined the game
     */
    private void checkLoadReady() {
        synchronized (clientsLock) {
            if (clients.entrySet().stream().noneMatch(entry -> entry.getValue() == null || !entry.getValue().isConnected())) {
                waitForLoad = false;
                controllerGame.sendPrivateUpdates();
            }
        }
    }

    /**
     * Checks if a username is legit by checking that is not equal to a forbidden username
     *
     * @param username username to check
     * @return if a username is legit
     */
    private boolean isUsernameLegit(String username) {
        for (String forbidden : GameConstants.getForbiddenUsernames()) {
            if (username.equalsIgnoreCase(forbidden)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Process a message sent to server
     *
     * @param message message sent to server
     */
    void onMessage(Message message) {
        if (message != null && message.getSenderUsername() != null && (message.getToken() != null || message.getSenderUsername().equals("god"))) {
            if (message.getContent().equals(MessageContent.SHOOT)) {
                String messageString = message.toString();
                LOGGER.log(Level.INFO, messageString);
            } else {
                LOGGER.log(Level.INFO, "Received: {0}", message);
            }

            String msgToken = message.getToken();
            Connection conn;

            synchronized (clientsLock) {
                conn = clients.get(message.getSenderUsername());
            }

            if (conn == null) {
                LOGGER.log(Level.INFO, "Message Request {0} - Unknown username {1}", new Object[]{message.getContent().name(), message.getSenderUsername()});
            } else if (msgToken.equals(conn.getToken())) { // Checks that sender is the real player
                Message response = controllerGame.onMessage(message);

                updateTimer();

                // send message to client
                sendMessage(message.getSenderUsername(), response);
            }
        }
    }

    /**
     * Updates the timer state
     */
    private void updateTimer() {
        if (Game.getInstance().isGameStarted()) {
            Connection conn;

            synchronized (clientsLock) {
                conn = clients.get(controllerGame.getTurnOwnerUsername());
            }

            moveTimer.cancel();
            moveTimer = new Timer();
            moveTimer.schedule(new MoveTimer(conn, controllerGame.getTurnOwnerUsername()), moveTime);

            LOGGER.log(Level.INFO, "Move timer reset for user {0}, {1} seconds left", new Object[]{controllerGame.getTurnOwnerUsername(), moveTime / 1000});
        }
    }

    /**
     * Called when a player disconnects
     *
     * @param playerConnection connection of the player that just disconnected
     */
    void onDisconnect(Connection playerConnection) {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            LOGGER.log(Level.INFO, "{0} disconnected from server!", username);

            if (controllerGame.getGameState() == PossibleGameState.GAME_ROOM) {
                synchronized (clientsLock) {
                    clients.remove(username);
                }
                controllerGame.onMessage(new LobbyMessage(username, null, null, true));
                LOGGER.log(Level.INFO, "{0} removed from client list!", username);
            } else {
                controllerGame.onConnectionMessage(new LobbyMessage(username, null, null, true));
                sendMessageToAll(new DisconnectionMessage(username));
            }
        }
    }

    /**
     * Sends a message to all clients
     *
     * @param message message to send
     */
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

    /**
     * Sends a message to a client
     *
     * @param username username of the client who will receive the message
     * @param message  message to send
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
     * Returns the username of the connection owner
     *
     * @param connection connection to check
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
     * Process that pings all the clients to check if they are still connected
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
}
