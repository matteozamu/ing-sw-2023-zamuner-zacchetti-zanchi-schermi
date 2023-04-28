package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Handles the game advancement and listen for the reception of messages
 */
public abstract class ClientGameManager implements ClientGameManagerListener, ClientUpdateListener, Runnable {
    public static final Logger LOGGER = Logger.getLogger("adrenaline_client");

    public static final String SEND_ERROR = "Error while sending the request";
    public static final String ERROR_DIALOG_TITLE = "Error";
    public static final String TELEPORTER = "TELEPORTER";
    public static final String NEWTON = "NEWTON";
    public static final String TAGBACK_GRENADE = "TAGBACK GRENADE";
    public static final String TARGETING_SCOPE = "TARGETING SCOPE";
    protected static final String INVALID_STRING = "Invalid String!";
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final Object gameSerializedLock = new Object(); // handles GameSerialized parallelism

    private Client client;
    private boolean joinedLobby;
    private List<String> lobbyPlayers;

    private boolean votedMap;

    private ClientRoundManager roundManager; // manage the rounds of this client
    private ClientUpdater clientUpdater;

    private String firstPlayer;
    private String turnOwner;
    private boolean frenzyJustActivated = false;
    private boolean turnOwnerChanged;
    private boolean waitingGrenade = false;
    private boolean loadGame = false;

    private boolean firstTurn;
    private boolean yourTurn;

    private boolean isBotPresent;
    private boolean botRequest;

    private boolean noChangeStateRequest; // Identify a request that doesn't have to change the player state

    private boolean gameEnded = false;

    public ClientGameManager() {
        firstTurn = true;
        noChangeStateRequest = false;
        turnOwnerChanged = false;

        joinedLobby = false;

        Date date = GregorianCalendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm_HH.mm.ss");

        try {
            FileHandler fh = new FileHandler("log/client-" + dateFormat.format(date) + ".log");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                queue.take().run();
            } catch (InterruptedException e) {
                LOGGER.severe(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void onUpdate(Message message) {
        if (gameEnded) {
            return;
        }

        switch (message.getContent()) {
            case CONNECTION_RESPONSE:
//                handleConnectionResponse((ConnectionResponse) message);
                break;

            case RESPONSE:
//                handleResponse((Response) message);
                break;

            case READY:
//                handleGameStartMessage((GameStartMessage) message);
                break;

            case GAME_LOAD:
//                handleGameLoad((GameLoadResponse) message);
                break;

            default:
        }

        LOGGER.log(Level.INFO, "Received: {0}", message);
    }

    /**
     * Creates a connection with the server based on user input data
     *
     * @param connection type of connection (0 for Socket, 1 for RMI)
     * @param username   username of the player
     * @param address    address of the server
     * @param port       port of the serve
     * @throws Exception if something goes wrong with the creation of the connection
     */
    public void createConnection(int connection, String username, String address, int port, DisconnectionListener disconnectionListener) throws Exception {
        if (connection == 0) {
            client = new ClientSocket(username, address, port, disconnectionListener);
        } else {
            client = new ClientRMI(username, address, port, disconnectionListener);
        }

        client.startConnection();
    }

    /**
     * Closes the connection with the server
     */
    public void closeConnection() {
        if (clientUpdater != null) {
            clientUpdater.stop();
            clientUpdater = null;
        }

        try {
            client.close();
        } catch (Exception e) {
            // No issues
        }
        client = null;
    }

    /**
     * @return the client username
     */
    public String getUsername() {
        return client.getUsername();
    }

    /**
     * @return the client toke
     */
    public String getClientToken() {
        return client.getToken();
    }

    /**
     * @return the turn owner
     */
    public String getTurnOwner() {
        return turnOwner;
    }

    /**
     * @return the username of the first player of the game
     */
    public String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * @return the players in lobby
     */
    public List<String> getLobbyPlayers() {
        return lobbyPlayers;
    }

    /**
     * Sets the voted map flag to true
     */
    protected void votedMap() {
        votedMap = true;
    }

    public boolean isBotPresent() {
        return isBotPresent;
    }
}
