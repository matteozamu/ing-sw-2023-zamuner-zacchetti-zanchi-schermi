package it.polimi.ingsw.control;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.PlayersNumberReply;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.message.MessageType.PLAYERS_NUMBER_REPLY;
import static it.polimi.ingsw.model.Board.Direction.*;

/**
 * Controller for the game, handling game logic and interactions between model components.
 */
public class ControllerGame implements Observer, Serializable {
    private static final long serialVersionUID = 4951303731052728724L;
    private static final String STR_INVALID_STATE = "Invalid game state!";
    private Game game;
    private transient Map<String, VirtualView> virtualViewMap;
    private List<ObjectCard> limbo;
    private GameState gameState;
    private TurnController turnController;
    private InputController inputController;
    private CommonGoal[] commonGoals = new CommonGoal[2];


    /**
     * Constructor for the ControllerGame class, initializing the game state.
     */
    public ControllerGame() {
        this.game = Game.getInstance();
//        this.numberOfPlayers = 0;
        this.limbo = new ArrayList<>();
        this.virtualViewMap = Collections.synchronizedMap(new HashMap<>());
        this.inputController = new InputController(virtualViewMap, this);
        setGameState(GameState.LOGIN);
    }

    /**
     * set the state of the game
     *
     * @param gameState is the state to set
     */
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    //***** INIT METHODS *****//

    /**
     * Handles the login of a player. If the player is new, his VirtualView is saved, otherwise it's discarded and the player is notified.
     * If it's the first Player then ask number of Players he wants, add Player to the Game otherwise change the GameState.
     *
     * @param username    the username of the player.
     * @param virtualView the virtualview of the player.
     */
    public void loginHandler(String username, VirtualView virtualView) {

        if (virtualViewMap.isEmpty()) { // First player logged. Ask number of players.
            addVirtualView(username, virtualView);
            // TODO completare personal goal
            game.addPlayer(new Player(username, new Shelf(), game.getRandomAvailablePersonalGoalCard()));
            System.out.println(game.getPlayers());

            virtualView.showLoginResult(true, true, Game.SERVER_NICKNAME);
            virtualView.askPlayersNumber();

        } else if (virtualViewMap.size() < game.getChosenPlayersNumber()) {
            addVirtualView(username, virtualView);
            game.addPlayer(new Player(username, new Shelf(), new PersonalGoalCard(null)));
            virtualView.showLoginResult(true, true, Game.SERVER_NICKNAME);

            if (game.getNumCurrentPlayers() == game.getChosenPlayersNumber()) { // If all players logged
//                // check saved matches.
//                StorageData storageData = new StorageData();
//                GameController savedGameController = storageData.restore();
//                if (savedGameController != null &&
//                        game.getPlayersNicknames().containsAll(savedGameController.getTurnController().getNicknameQueue())) {
//                    restoreControllers(savedGameController);
//                    broadcastRestoreMessages();
//                    Server.LOGGER.info("Saved Match restored.");
//                    turnController.newTurn();
//                } else {
//                    initGame();
//                }
                // da togliere
                initGame();
                startGame();
            }
        } else {
            virtualView.showLoginResult(true, false, Game.SERVER_NICKNAME);
        }
    }

    /**
     * create the board and select two common goal card
     */
    private void initGame(){
        setGameState(GameState.INIT);
        commonGoals[0] = game.getRandomAvailableCommonGoal();
        commonGoals[1] = game.getRandomAvailableCommonGoal();

    }


    /**
     * Change gameState into IN_GAME and create the board. Initialize TurnController
     */
    private void startGame() {
        setGameState(GameState.IN_GAME);
        turnController = new TurnController(virtualViewMap, this);
        broadcastGenericMessage("All Players are connected. ");
        broadcastGenericMessage("Game Started!");

        turnController.newTurn();
    }


    /**
     * Switch on Game State.
     *
     * @param receivedMessage Message from Active Player.
     */
    public void onMessageReceived(Message receivedMessage) {

        VirtualView virtualView = virtualViewMap.get(receivedMessage.getUsername());
        switch (gameState) {
            case LOGIN:
                loginState(receivedMessage);
                break;
//            case INIT:
//                if (inputController.checkUser(receivedMessage)) {
//                    initState(receivedMessage, virtualView);
//                }
//                break;
//            case IN_GAME:
//                if (inputController.checkUser(receivedMessage)) {
//                    inGameState(receivedMessage);
//                }
//                break;
            default: // Should never reach this condition
                Server.LOGGER.warning(STR_INVALID_STATE);
                break;
        }
    }

    //***** 3 MAIN STATE METHODS *****//

    /**
     * Switch on Login Messages' Types.
     *
     * @param receivedMessage Message from Active Player.
     */
    private void loginState(Message receivedMessage) {
        if (receivedMessage.getMessageType() == PLAYERS_NUMBER_REPLY) {
            if (inputController.verifyReceivedData(receivedMessage)) {
                game.setChosenMaxPlayers(((PlayersNumberReply) receivedMessage).getPlayerNumber());
                broadcastGenericMessage("Waiting for other Players . . .");
            }
        } else {
            Server.LOGGER.warning("Wrong message received from client.");
        }
    }


    //***** VIRTUAL VIEW METHODS *****//

    /**
     * Adds a Player VirtualView to the controller if the first player max_players is not exceeded.
     * Then adds a controller observer to the view.
     * Adds the VirtualView to the game and board model observers.
     *
     * @param nickname    the player nickname to identify his associated VirtualView.
     * @param virtualView the VirtualView to be added.
     */
    public void addVirtualView(String nickname, VirtualView virtualView) {
        virtualViewMap.put(nickname, virtualView);
        game.addObserver(virtualView);
//        game.getBoard().addObserver(virtualView);
    }

    /**
     * Sends a Message which contains Game Information to every {@link Player} in Game.
     *
     * @param messageToNotify Message to send.
     */
    public void broadcastGenericMessage(String messageToNotify) {
        for (VirtualView vv : virtualViewMap.values()) {
            vv.showGenericMessage(messageToNotify);
        }
    }

//    public Player getCurrentPlayer() {
//        return currentPlayer;
//    }
//
//    public void setCurrentPlayer(Player currentPlayer) {
//        this.currentPlayer = currentPlayer;
//    }

    /**
     * @return the Game classe
     */
    // forse non serve, viene chiamato solo nei test
    public Game getGame() {
        return game;
    }

    /**
     * Checks if the game is already started, then no more players can connect.
     *
     * @return {@code true} if the game isn't started yet, {@code false} otherwise.
     */
    public boolean isGameStarted() {
        return this.gameState != GameState.LOGIN;
    }

    /**
     * @return the array list containing the one, two or three ObjectCard selected
     */
    public List<ObjectCard> getLimbo() {
        return limbo;
    }

    /**
     * Check if the username is available
     *
     * @param username is the username of the player
     * @return true if available, false if not
     * @throws NullPointerException if username is null
     */

    /**
     * Verifies the nickname through the InputController.
     *
     * @param username the nickname to be checked.
     * @param view     the view of the player who is logging in.
     * @return true if the clien can be logged to the game
     */
    public boolean checkLoginUsername(String username, View view) {
        return inputController.checkLoginUsername(username, view);
    }

    //TESTED
    public boolean isUsernameAvailable(String username) throws NullPointerException {
        if (username == null) throw new NullPointerException("Username is null");
        for (Player p : this.game.getPlayers()) {
            if (p.getName().equals(username)) return false;
        }
        return true;
    }

    /**
     * Fills the game board with object cards based on the number of players.
     * This method should be called at the beginning of the game to set up the board.
     */
    // TODO: parametrizzare sul numero di giocatori
    // TODO: non sarebbe meglio spostarlo nel model?
    public void fillBoard() {
        Coordinate c;
        try {
            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(5 - row, -5 + col);
                    this.game.getBoard().createCell(c, game.getRandomAvailableObjectCard());
                }
            }
            for (int row = 5 - 1; row >= 1; row--) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(-5 + row, -5 + col);
                    this.game.getBoard().createCell(c, game.getRandomAvailableObjectCard());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // TODO: da spostare nella view

    /**
     * Select the column where the user want to add che ObjectCard, need to check if there is enough space
     *
     * @param column is where the user want to add che ObjectCard
     */
    public void selectColumn(int column) {
        System.out.println("Seleziona una colonna: [0, 1, 2, 3, 4]");
        while (game.getCurrentPlayer().getShelf().getFreeCellsPerColumn(column) < limbo.size()) {
            System.out.println("La colonna selezionata non ha abbastanza spazi");
            System.out.println("Seleziona una colonna: [0, 1, 2, 3, 4]");
        }
    }

    //TODO aggiungere controllo su colonne disponibili e chiamare metodo del game addObjectCardsToShelf

//    /**
//     * Load the shelf with the ObjectCard, the order has already been established
//     * @param column is the number of the column where the ObjectCard is added
//     * @param objectCard is the ObjectCard to add in the current player's shelf
//     */
//    public void loadShelf(int column, List<ObjectCard> objectCard) {
//        currentPlayer.getShelf().addObjectCards(column, objectCard);
//    }

    //si puo fare una modifica che non rimuova la coordinata della cella ma setti il contenuto a null


    /**
     * Checks if the object card at the given coordinate is available (i.e., has at least one free side).
     * This method is used to determine if a player can pick up an object card from the board.
     *
     * @param coordinate The coordinate of the object card to check.
     * @return True if the object card is available, false otherwise.
     */
    //TESTED
    public boolean isObjectCardAvailable(Coordinate coordinate) {
        return this.game.getBoard().isEmptyAtDirection(coordinate, UP) || this.game.getBoard().isEmptyAtDirection(coordinate, DOWN) || this.game.getBoard().isEmptyAtDirection(coordinate, RIGHT) || this.game.getBoard().isEmptyAtDirection(coordinate, LEFT);
    }

    /**
     * Adds the object card at the specified coordinate to the limbo area.
     * The limbo area is used to store object cards that a player has picked up but not yet placed on their shelf.
     *
     * @param card The object card to add to the limbo area.
     * @throws NullPointerException If the object card is null (should not happen).
     */

    //TESTED
    public boolean addObjectCardToLimbo(ObjectCard card) throws NullPointerException {
        if (card == null) throw new NullPointerException("ObjectCard is null");
        if (this.limbo.size() == 3) return false;

        this.limbo.add(card);
        return true;
    }

    /**
     * pick the ObjectCard from the board
     *
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return the ObjectCard with that Coordinate
     */
    public ObjectCard pickObjectCard(Coordinate coordinate) {
        if (isObjectCardAvailable(coordinate)) {
            return this.game.getBoard().removeObjectCard(coordinate);
        } else return null;
    }

    /**
     * Calculate the points of the currentPlayer. Each time the method counts the points starting from 0.
     * Then set the points to the currentPlayer.
     *
     * @return the point of the currentPlayer
     */

    //TESTED
    public int pointsCalculator() {
        int points = 0;

        points += this.game.getCurrentPlayer().getPersonalGoalCard().calculatePoints();
        for (CommonGoal c : this.game.getCommonGoals()) {
            if (c.checkGoal(this.game.getCurrentPlayer().getShelf()))
                points += c.updateCurrentPoints(this.game.getPlayers().size());
        }
        points += this.game.getCurrentPlayer().getShelf().closeObjectCardsPoints();
        if (this.game.getCurrentPlayer().getShelf().isFull()) points++;

        this.game.getCurrentPlayer().setCurrentPoints(points);

        return points;
    }

    /**
     * Receives an update message from the effect model.
     *
     * @param message the update message.
     */
    @Override
    public void update(Message message) {


    }
}

