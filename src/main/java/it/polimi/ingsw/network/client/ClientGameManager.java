package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleAction;
import it.polimi.ingsw.enumeration.UserPlayerState;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.GameSerialized;
import it.polimi.ingsw.model.Player;
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
    private final Object gameSerializedLock = new Object();
    private Client client;
    private boolean joinedLobby;
    private List<String> lobbyPlayers;
    private String firstPlayer = null;
    private String turnOwner;
    private boolean firstTurn;
    private boolean yourTurn;
    private ClientTurnManager turnManager; // manage the rounds of this client
    private ClientUpdater clientUpdater;
    private boolean gameEnded = false;
    private GameSerialized gameSerialized;

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

            case GAME_STATE:
                handleGameStateResponse((GameStateResponse) message);
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

    public GameSerialized getGameSerialized() {
        synchronized (gameSerializedLock) {
            return gameSerialized;
        }
    }

    private void handleGameStartMessage(GameStartMessage gameStartMessage) {
        synchronized (gameSerializedLock) {
            firstPlayer = gameStartMessage.getFirstPlayer();
            gameSerialized = gameStartMessage.getGameSerialized();
        }

        turnOwner = gameStartMessage.getFirstPlayer();
        startGame(gameStartMessage.getCommonGoals());
    }

    public boolean sendRequest(Message message) {
        if (turnManager != null) {
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
     * Handles the update of the game state
     *
     * @param gameStateMessage game state update received
     */
    // il game state lo mandiamo all'inizio di ogni turno? o anche durante le fasi di gioco in un turno?
    private void handleGameStateResponse(GameStateResponse gameStateMessage) {
        synchronized (gameSerializedLock) {
            gameSerialized = gameStateMessage.getGameSerialized();
        }

        queue.add(this::gameStateUpdate);

        // TODO verificare se e come farlo
//        checkTurnChange(gameStateMessage);
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

            if (lobbyPlayers.size() == 1) queue.add(() -> numberOfPlayersRequest(response));
            queue.add(() -> lobbyJoinResponse(response));
        } else {
            if (response.getStatus() == MessageStatus.ERROR) {
                queue.add(() -> responseError(response.getMessage()));
            } else {
                onPositiveResponse(response);
            }
        }
        if (firstPlayer != null) checkNextAction();
    }

    /**
     * Check what is the next action for the client
     */
    private void checkNextAction() {
        if (turnManager.getUserPlayerState() != UserPlayerState.END) {
            makeMove();
        } else {
            turnManager.endTurn();
        }

//        if (yourTurn && turnOwnerChanged) {
//            turnOwnerChanged = false;
//            yourTurn = false;
//
//            newTurn();
//        }
    }

    private void onPositiveResponse(Response response) {
        if (response.getStatus() == MessageStatus.PRINT_LIMBO) {
            queue.add(() -> printLimbo());
        }
        if (turnManager != null) {
            turnManager.nextState();
        }
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

    /**
     * Returns a player based on the provided username
     *
     * @return the player of the client
     */
    public Player getPlayer() {
        synchronized (gameSerializedLock) {
            return gameSerialized.getPlayers().stream().filter(p -> p.getName().equals(getUsername())).findFirst().orElse(null);
        }
    }

    /**
     * @return a list of all the players
     */
    public List<Player> getPlayers() {
        synchronized (gameSerializedLock) {
            return gameSerialized.getPlayers();
        }
    }

    private void startGame(List<CommonGoal> cg) {
        turnManager = new ClientTurnManager();

        if (firstTurn) {
            if (firstPlayer.equals(getUsername())) { // First player to play
                yourTurn = true;
            }

            queue.add(() -> firstPlayerCommunication(firstPlayer, cg));
            queue.add(this::gameStateUpdate);
            // TODO cosi stampa prima inizio gioco poi chiede lo stato ma problema di sincro con la ricezione messaggi
//            queue.add(() -> gameStateRequest(getUsername(), getClientToken()));

            firstTurn = false;
        }

        newTurn();
    }

    /**
     * Called when a change of turn owner happen
     */
    private void newTurn() {
        if (yourTurn) {
            turnManager.startTurn();
            makeMove();
        } else {
            queue.add(() -> notYourTurn(turnOwner));
        }
    }

    /**
     * Show the client all the possible actions
     */
    public void makeMove() {
        if (getUsername().equals(turnOwner)) {
            queue.add(() -> displayActions(getPossibleActions()));
        }
    }

    /**
     * @return a list of possible actions based on the current state of the player
     */
    private List<PossibleAction> getPossibleActions() {
        switch (turnManager.getUserPlayerState()) {
            case PICK_CARD_BOARD:
                return List.of(PossibleAction.BOARD_PICK_CARD);

            case AFTER_FIRST_PICK: {
                if(gameSerialized.getLimbo().size() == 3){
                    return List.of(PossibleAction.LOAD_SHELF, PossibleAction.REORDER_LIMBO, PossibleAction.DELETE_LIMBO);
                } else if (gameSerialized.getLimbo().size() > 1) {
                    return List.of(PossibleAction.BOARD_PICK_CARD, PossibleAction.LOAD_SHELF, PossibleAction.REORDER_LIMBO, PossibleAction.DELETE_LIMBO);
                } else {
                    return List.of(PossibleAction.BOARD_PICK_CARD, PossibleAction.LOAD_SHELF, PossibleAction.DELETE_LIMBO);
                }
            }

            case ENDING_PHASE:
//                return getEndingActions();

//            case DEAD:
//                return List.of(PossibleAction.CHOOSE_RESPAWN);

            default:
                return null;
//                throw new ClientRoundManagerException("Cannot be here: " + roundManager.getUserPlayerState().name());
        }
    }

    /**
     * Executes the action chosen
     *
     * @param chosenAction action chosen by the user
     */
    public void doAction(PossibleAction chosenAction) {
        Runnable action = null;

        switch (chosenAction) {
            case BOARD_PICK_CARD:
                System.out.println("SCEGLI CARTA");
                action = this::pickBoardCard;
                break;
            case LOAD_SHELF:
                System.out.println("SCEGLI COLONNA");
                action = this::chooseColumn;
                break;
            case REORDER_LIMBO:
                System.out.println("SCEGLI ORDINE");
                action = this::reorderLimbo;
                break;
            case DELETE_LIMBO:
                System.out.println("SCEGLI CARTA DA ELIMINARE");
                action = this::deleteLimbo;
                break;

            default:
//                throw new ClientRoundManagerException("Invalid Action");
        }

        queue.add(action);
    }
}
