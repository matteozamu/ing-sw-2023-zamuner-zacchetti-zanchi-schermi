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
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class ClientGameManager implements ClientGameManagerListener, ClientUpdateListener, Runnable {
    public static final Logger LOGGER = Logger.getLogger("my_shelfie_client");

    public static final String ERROR_DIALOG_TITLE = "Error";
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
    private boolean turnOwnerChanged;
    private ClientTurnManager turnManager; // manage the rounds of this client
    private ClientUpdater clientUpdater;
    private boolean gameEnded = false;
    private GameSerialized gameSerialized;
    private boolean reconnection = false;

    /**
     * constructor of the client game manager
     */
    public ClientGameManager() {
        firstTurn = true;
        joinedLobby = false;
        turnOwnerChanged = false;

        Date date = GregorianCalendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm_HH.mm.ss");

        try {
            FileHandler fh = new FileHandler("log/client-" + dateFormat.format(date) + ".log");
            fh.setFormatter(new SimpleFormatter());
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fh);
        } catch (IOException e) {
//            LOGGER.severe(e.getMessage());
        }

        new Thread(this).start();
    }

    /**
     * method to run a client thread
     */
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

    /**
     * @return the client's username
     */
    public String getUsername() {
        return client.getUsername();
    }

    /**
     * @return the client's token
     */
    public String getClientToken() {
        return client.getToken();
    }

    /**
     * method called when a message is received from the server
     *
     * @param message the received message from the server
     */
    @Override
    public void onUpdate(Message message) {
//        LOGGER.log(Level.INFO, "Received: {0}", message);

        if (gameEnded) {
            return;
        }

        switch (message.getContent()) {
            case CONNECTION_RESPONSE:
                handleConnectionResponse((ConnectionResponse) message);
                break;

            case LIST_GAME:
                handleGameListResponse((ListGameResponse) message);
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

            case GAME_ENDED:
                handleGameEnded((EndGameMessage) message);
                break;

            case DISCONNECTION:
                handleDisconnection((DisconnectionMessage) message);
                break;

            case RECONNECTION:
                handleReconnection((ReconnectionMessage) message);
                break;

            case RECONNECTION_REQUEST:
                handleReconnectionRequest((ReconnectionRequest) message);
                break;

            default:
        }
    }

    /**
     * @return the game serialized
     */
    public GameSerialized getGameSerialized() {
        synchronized (gameSerializedLock) {
            return gameSerialized;
        }
    }

    /**
     * method used to handle the initial phase of the game
     *
     * @param gameStartMessage is the message received from the server
     */
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
    private void handleGameStateResponse(GameStateResponse gameStateMessage) {
        synchronized (gameSerializedLock) {
            gameSerialized = gameStateMessage.getGameSerialized();
        }

        queue.add(this::gameStateUpdate);

        checkTurnChange(gameStateMessage);
    }

    /**
     * Checks if the turn owner is changed
     *
     * @param stateMessage game state message received
     */
    private void checkTurnChange(GameStateResponse stateMessage) {
        if (!firstTurn) {
            if (!getGameSerialized().getCurrentPlayer().getName().equals(turnOwner)) {
                turnOwner = stateMessage.getTurnOwner();
                turnOwnerChanged = true;
            }

            if (!yourTurn) { // If you are not the turn owner you don't need to wait a response
                turnOwnerChanged = false;

                if (turnOwner.equals(getUsername())) {
                    yourTurn = true;
                }
                newTurn();
            }
        }
    }

    /**
     * method that handles the message received with the list of available games
     *
     * @param message is the message received from the server
     */
    private void handleGameListResponse(ListGameResponse message) {
        if (message.getGames().isEmpty()) {
            queue.add(this::noGameAvailable);
            queue.add(() -> displayActions(getLobbyActions()));
        } else queue.add(() -> chooseGameToJoin(message.getGames()));
    }

    private void handleReconnectionRequest(ReconnectionRequest reconnectionRequest) {
        reconnection = true;
//        if (reconnectionRequest.getStatus().equals(MessageStatus.OK)) {
        client.setToken(reconnectionRequest.getToken());
//        } else {
//            client.pingTimer.cancel();
//            closeConnection();
//        }

//        queue.add(() -> connectionResponse(connectionResponse));
        turnManager.startTurn();
//        queue.add(() -> displayActions(getPossibleActions()));
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
        queue.add(() -> displayActions(getLobbyActions()));
    }

    /**
     * handle adding a player to the game
     *
     * @param message is the message received from the server
     */
    private void handlePlayersInLobby(LobbyPlayersResponse message) {
        lobbyPlayers = message.getUsers();
        queue.add(() -> playersWaitingUpdate(message.getUsers()));
    }

    /**
     * handle a response message
     *
     * @param response is the message received from the server
     */
    private void handleResponse(Response response) {
        if (response.getStatus() == MessageStatus.GAME_CREATED || response.getStatus() == MessageStatus.GAME_JOINED) {
            queue.add(this::addPlayerToGameRequest);
        } else {
            if (!joinedLobby) {
                if (response.getStatus() == MessageStatus.ERROR) {
                    queue.add(() -> responseError(response.getMessage()));
                    return;
                }
                joinedLobby = response.getStatus() == MessageStatus.OK;

                if (lobbyPlayers.size() == 1) {
                    queue.add(() -> numberOfPlayersRequest(response));
                    queue.add(() -> lobbyJoinResponse(response));
                    return;
                } else
                    // TODO check if this is correct
                    queue.add(() -> lobbyJoinResponse(response));
            } else {
                if (turnManager != null) System.out.println("TURN MANAGER: " + turnManager.getUserPlayerState());
                if (response.getStatus() == MessageStatus.ERROR) {
//                    queue.add(() -> responseError(response.getMessage()));
                } else if (response.getStatus() == MessageStatus.NOT_VALID_CARD) {
                    queue.add(() -> responseError("The card chosen is not valid"));
                } else if (turnManager != null && turnManager.getUserPlayerState().equals(UserPlayerState.LOADING_SHELF)) {
                    queue.add(() -> responseError("This column is full"));
                } else {
                    onPositiveResponse(response);
                }
            }
            if (firstPlayer != null || reconnection) checkNextAction();
        }
    }

    /**
     * handle the end of the game
     *
     * @param message is the message received from the server
     */
    private void handleGameEnded(EndGameMessage message) {
        gameEnded = true;
        synchronized (gameSerializedLock) {
            gameSerialized = message.getGameSerialized();
        }
        queue.add(() -> printWinner(gameSerialized));
    }

    public UserPlayerState getUserPlayerState() {
        return turnManager.getUserPlayerState();
    }

    /**
     * Handles a disconnection message received from the server when a clìient disconnects
     *
     * @param disconnectionMessage disconnection message received
     */
    private void handleDisconnection(DisconnectionMessage disconnectionMessage) {
        queue.add(() -> onPlayerDisconnection(disconnectionMessage.getUsername()));
    }

    private void handleReconnection(ReconnectionMessage reconnectionMessage) {
        this.firstTurn = false;
        this.joinedLobby = true;

        if (!reconnectionMessage.getCurrentPlayer().equals(getUsername())) {
            yourTurn = false;
        }

        turnManager = new ClientTurnManager();
        queue.add(() -> onPlayerReconnection(reconnectionMessage.getMessage()));
    }

    /**
     * Check what is the next action for the client
     */
    private void checkNextAction() {
        // se il giocatore è disconnesso creo un nuovo turno
        // TODO gestire il caso di un singolo giocatore
        if (!gameSerialized.getCurrentPlayer().isConnected()) {
            newTurn();
        } else {
            if (turnManager.getUserPlayerState() != UserPlayerState.ENDING_PHASE) {
                makeMove();
            } else {
                turnManager.endTurn();
            }

//        System.out.println(yourTurn + "  -  " + turnOwnerChanged);
            if (yourTurn && turnOwnerChanged) {
                turnOwnerChanged = false;
                yourTurn = false;

                newTurn();
            }
        }
    }

    /**
     * method used to handle a positive response
     *
     * @param response is the positive response received from the server
     */
    private void onPositiveResponse(Response response) {
        if (response.getStatus() == MessageStatus.PRINT_LIMBO) {
            queue.add(this::printLimbo);
        }
        if (response.getStatus() == MessageStatus.GAME_ENDED) {
            gameEnded = true;
            queue.add(() -> printEndGame(response.getMessage()));
        }
        if (turnManager != null) {
            turnManager.nextState();
        }
    }

    /**
     * method used to create a connection with the server
     *
     * @param connection            can be 0 if the user choose a socket connection or 1 if the user choose a RMI connection
     * @param username              is the chosen username
     * @param address               is the server address
     * @param port                  is the server port
     * @param disconnectionListener is the listener of client disconnection
     * @throws Exception can throw an exception if the connection can't be created
     */
    public void createConnection(int connection, String username, String address, int port, DisconnectionListener disconnectionListener) throws Exception {
        if (connection == 0) {
            client = new ClientSocket(username, address, port, disconnectionListener);
        } else {
            client = new ClientRMI(username, address, port, disconnectionListener);
        }

        client.startConnection();
        startUpdater();
    }

    /**
     * method used to close a connection
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
     * method that start a client updater
     */
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

    /**
     * @return the players in lobby
     */
    public List<String> getLobbyPlayers() {
        return lobbyPlayers;
    }

    /**
     * @return the turn owner
     */
    public String getTurnOwner() {
        return turnOwner;
    }

    /**
     * method used to start the game
     *
     * @param cg the list of the common goal of the game
     */
    private void startGame(List<CommonGoal> cg) {
        turnManager = new ClientTurnManager();

        if (firstTurn) {
            if (firstPlayer.equals(getUsername())) { // First player to play
                yourTurn = true;
            }

            queue.add(() -> firstPlayerCommunication(firstPlayer, cg));
            queue.add(this::gameStateUpdate);
            // TODO cosi stampa prima inizio game poi chiede lo stato ma problema di sincro con la ricezione messaggi
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
    private List<PossibleAction> getLobbyActions() {
        return List.of(PossibleAction.JOIN_GAME, PossibleAction.CREATE_GAME);

    }


    /**
     * @return a list of possible actions based on the current state of the player
     */
    private List<PossibleAction> getPossibleActions() {
        List actions = new ArrayList();
        switch (turnManager.getUserPlayerState()) {
            case PICK_CARD_BOARD -> {
                actions.add(PossibleAction.BOARD_PICK_CARD);
//                actions = List.of(PossibleAction.BOARD_PICK_CARD, PossibleAction.SHOW_PERSONAL_GOAL, PossibleAction.SHOW_SHELF);
            }
            case AFTER_FIRST_PICK -> {
                if (gameSerialized.getLimbo().size() == 3) {
                    actions.add(PossibleAction.LOAD_SHELF);
                    actions.add(PossibleAction.REORDER_LIMBO);
                    actions.add(PossibleAction.DELETE_LIMBO);
//                    actions = List.of(PossibleAction.LOAD_SHELF, PossibleAction.REORDER_LIMBO, PossibleAction.DELETE_LIMBO, PossibleAction.SHOW_PERSONAL_GOAL, PossibleAction.SHOW_SHELF);
                } else if (gameSerialized.getLimbo().size() > 1) {
                    actions.add(PossibleAction.BOARD_PICK_CARD);
                    actions.add(PossibleAction.LOAD_SHELF);
                    actions.add(PossibleAction.REORDER_LIMBO);
                    actions.add(PossibleAction.DELETE_LIMBO);
                    //actions = List.of(PossibleAction.BOARD_PICK_CARD, PossibleAction.LOAD_SHELF, PossibleAction.REORDER_LIMBO, PossibleAction.DELETE_LIMBO, PossibleAction.SHOW_PERSONAL_GOAL, PossibleAction.SHOW_SHELF);
                } else {
                    actions.add(PossibleAction.BOARD_PICK_CARD);
                    actions.add(PossibleAction.LOAD_SHELF);
                    actions.add(PossibleAction.DELETE_LIMBO);
                    //actions = List.of(PossibleAction.BOARD_PICK_CARD, PossibleAction.LOAD_SHELF, PossibleAction.DELETE_LIMBO, PossibleAction.SHOW_PERSONAL_GOAL, PossibleAction.SHOW_SHELF);
                }
            }
            case LOADING_SHELF -> {
                actions.add(PossibleAction.LOAD_SHELF);
                //actions.add(PossibleAction.SHOW_PERSONAL_GOAL);
                //actions.add(PossibleAction.SHOW_SHELF);
            }
            default -> {
                return null;
            }
        }
        return actions;
    }

    /**
     * Executes the action chosen
     *
     * @param chosenAction action chosen by the user
     */
    public void doAction(PossibleAction chosenAction) {
        Runnable action = null;

        switch (chosenAction) {
            case JOIN_GAME -> {
//                System.out.println("JOIN GAME");
                action = this::joinGame;
            }
            case CREATE_GAME -> {
//                System.out.println("CREATE GAME");
                action = this::createGame;
            }
            case BOARD_PICK_CARD -> {
//                System.out.println("SCEGLI CARTA");
                action = this::pickBoardCard;
            }
            case LOAD_SHELF -> {
//                System.out.println("CARICA SHELF");
//                turnManager.loadingShelf();
                action = this::chooseColumn;
            }
            case REORDER_LIMBO -> {
//                System.out.println("SCEGLI ORDINE");
                action = this::reorderLimbo;
            }
            case SHOW_PERSONAL_GOAL -> {
//                System.out.println("SHOW PERSONAL GOAL");
                action = this::showPersonalGoal;
                checkNextAction();
            }
            case SHOW_SHELF -> {
//                System.out.println("SHOW SHELF");
                action = this::showShelf;
                checkNextAction();
            }
            case CANCEL -> {
                action = this::cancelAction;
                checkNextAction();
            }
            case DELETE_LIMBO -> {
//                System.out.println("ELIMINO LIMBO");
                turnManager.deleteLimbo();
                action = this::deleteLimbo;
            }
            default -> {
            }
//                throw new ClientRoundManagerException("Invalid Action");
        }

        assert action != null;
        queue.add(action);
    }
}
