package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.network.message.*;

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

public abstract class ClientGameManager implements ClientGameManagerListener, ClientUpdateListener, Runnable {
    public static final Logger LOGGER = Logger.getLogger("my_shelfie_client");

    public static final String SEND_ERROR = "Error while sending the request";
    protected static final String INVALID_STRING = "Invalid String!";
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    private Client client;
    private boolean joinedLobby;
    private List<String> lobbyPlayers;

    private String firstPlayer;
    private String turnOwner;
    private boolean firstTurn;
    private boolean yourTurn;

    private ClientTurnManager roundManager; // manage the rounds of this client
    //    private GameSerialized gameSerialized;
    private ClientUpdater clientUpdater;
    private boolean gameEnded = false;

    public ClientGameManager() {
        firstTurn = true;
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

    public String getUsername() {
        return client.getUsername();
    }

    public String getClientToken() {
        return client.getToken();
    }

    @Override
    public void onUpdate(Message message) {
        LOGGER.log(Level.INFO, "Received: {0}", message);

        if (gameEnded) {
            return;
        }

        switch (message.getContent()) {
            case CONNECTION_RESPONSE:
                handleConnectionResponse((ConnectionResponse) message);
                break;

            case PLAYERS_IN_LOBBY:
                handlePlayersInLobby((LobbyPlayersResponse) message);
                break;

            case RESPONSE:
                handleResponse((Response) message);
                break;

            case READY:
                handleGameStartMessage((GameStartMessage) message);
                break;

            case DISCONNECTION:
//                handleDisconnection((DisconnectionMessage) message);
                break;

            default:
        }
    }

    private void handleGameStartMessage(GameStartMessage gameStartMessage) {
//        synchronized (gameSerializedLock) {
        firstPlayer = gameStartMessage.getFirstPlayer();
        turnOwner = gameStartMessage.getFirstPlayer();
        startGame();
//        }
    }

    public boolean sendRequest(Message message) {
        if (roundManager != null) {
//            checkChangeStateRequest(message);
        }

        try {
            client.sendMessage(message);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Handles the response to the server connection
     *
     * @param connectionResponse response received
     */
    private void handleConnectionResponse(ConnectionResponse connectionResponse) {
        if (connectionResponse.getStatus().equals(MessageStatus.OK)) {
            client.setToken(connectionResponse.getNewToken());
        } else {
            client.pingTimer.cancel();
            closeConnection();
        }

        queue.add(() -> connectionResponse(connectionResponse));
    }

    private void handlePlayersInLobby(LobbyPlayersResponse message) {
        lobbyPlayers = message.getUsers();
        queue.add(() -> playersWaitingUpdate(message.getUsers()));
    }

    private void handleResponse(Response response) {
        if (!joinedLobby) {
            joinedLobby = response.getStatus() == MessageStatus.OK;

            if (lobbyPlayers.size() == 1) queue.add(() -> numberOfPlayersResponse(response));
            queue.add(() -> lobbyJoinResponse(response));
        }
        if (response.getStatus() == MessageStatus.ERROR) {
            queue.add(() -> responseError(response.getMessage()));
        } else {
            onPositiveResponse(response);
        }
    }

    private void onPositiveResponse(Response response) {
//        if (response.getStatus() == MessageStatus.NEED_PLAYER_ACTION) {
//            roundManager.targetingScope();
//        } else if (roundManager.getUserPlayerState() == UserPlayerState.ENDING_PHASE) {
//            roundManager.botRespawn();
//        } else {
//        }
    }

    public void createConnection(int connection, String username, String address, int port, DisconnectionListener disconnectionListener) throws Exception {
        if (connection == 0) {
            client = new ClientSocket(username, address, port, disconnectionListener);
        } else {
            client = new ClientRMI(username, address, port, disconnectionListener);
        }

        client.startConnection();
        startUpdater();
    }

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

    private void startUpdater() {
        clientUpdater = new ClientUpdater(client, this);
    }

    private void startGame() {
        // TODO fare start game
        roundManager = new ClientTurnManager();

        if (firstTurn) {
            if (firstPlayer.equals(getUsername())) { // First player to play
                yourTurn = true;
            }

            queue.add(() -> firstPlayerCommunication(firstPlayer));
//            queue.add(() -> boardPrint());
            firstTurn = false;
        }

        newTurn();
    }

    /**
     * Called when a change of turn owner happen
     */
    private void newTurn() {
//        if (loadGame) {
//            loadGame = false;
//        }

        if (yourTurn) {
            roundManager.beginRound();

//            makeMove();
        } else {
            queue.add(() -> notYourTurn(turnOwner));
        }
    }

    /**
     * Show the client all the possible actions
     */
//    protected void makeMove() {
//        if (getUsername().equals(turnOwner)) {
//            queue.add(() -> displayActions(getPossibleActions()));
//        }
//    }
}
