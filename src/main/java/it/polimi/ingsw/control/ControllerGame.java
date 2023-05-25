package it.polimi.ingsw.control;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleGameState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.JsonReader;
import it.polimi.ingsw.utility.TimerRunListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.Board.Direction.*;

//TODO: Eliminare il codice che non serve più

/**
 * Controller for the game, handling game logic and interactions between model components.
 */
public class ControllerGame implements TimerRunListener, Serializable {
    private final transient Server server;
    private UUID id;
    private Game game;
    private List<Coordinate> selectedCoordinates;
    private PossibleGameState gameState = PossibleGameState.GAME_ROOM;
    private boolean isLobbyFull;
    private transient TurnController turnController;

    /**
     * Constructor for the ControllerGame class, initializing the game state.
     */
    public ControllerGame(Server server) {
        JsonReader.readJsonConstant("GameConstant.json");
        this.server = server;
        this.id = UUID.randomUUID();
        this.game = Game.getInstance();
        this.selectedCoordinates = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    /**
     * method called when a message is received from a client
     *
     * @param receivedMessage is the message received
     * @return the response to the message
     */
    public Message onMessage(Message receivedMessage) {
        Server.LOGGER.log(Level.SEVERE, "ONMESSAGE: {0}", receivedMessage);

        System.out.println(gameState);

        if (gameState == PossibleGameState.GAME_ROOM) {
            return firstStateHandler(receivedMessage);
        }

        if (gameState == PossibleGameState.GAME_ENDED) {
            return new Response("GAME ENDED", MessageStatus.ERROR);
        }

        // TODO da rimettere
//        if (!InputValidator.validateInput(receivedMessage) || (gameState != PossibleGameState.GAME_ROOM && !this.getGame().doesPlayerExists(receivedMessage.getSenderUsername()))) {
//            return buildInvalidResponse();
//        }

        switch (receivedMessage.getContent()) {
            case GAME_STATE:
                return new GameStateResponse(receivedMessage.getSenderUsername(), game.getCurrentPlayer().getName());

            case PICK_OBJECT_CARD:
                return pickObjectCardHandler((ObjectCardRequest) receivedMessage);

            case REORDER_LIMBO_REQUEST:
                return reorderLimboHandler((ReorderLimboRequest) receivedMessage);

            case DELETE_LIMBO:
                return deleteLimboHandler((DeleteLimboRequest) receivedMessage);

            case LOAD_SHELF_REQUEST:
                return loadShelfHandler((LoadShelfRequest) receivedMessage);
        }

        return new Response("GAME STATE ERROR FOR THIS MESSAGE", MessageStatus.ERROR);
    }

    /**
     * method called when a LoadShelfRequest is received from a client
     *
     * @param LoadShelfRequest is the message received
     * @return the response to the message
     */
    private Response loadShelfHandler(LoadShelfRequest LoadShelfRequest) {
        int col = LoadShelfRequest.getColumn();

        if (LoadShelfRequest.getContent() == MessageContent.LOAD_SHELF_REQUEST && game.getCurrentPlayer().getShelf().getFreeCellsPerColumn(col) >= game.getLimbo().size()) {
            Server.LOGGER.log(Level.INFO, "Moving cards to shelf for player: {0}", LoadShelfRequest.getSenderUsername());
            loadShelf(new ArrayList<>(game.getLimbo().values()), col);

            this.pointsCalculator();

//           turnController.nextTurn();
            game.getLimbo().clear();
            if (game.getCurrentPlayer().getShelf().isFull()) {
                // manda un messaggio privato a tutti i giocatori contenente il vincitore
                calculateWinner();
                sendEndGame();
                changeState(PossibleGameState.GAME_ENDED);
                return new Response("Game is ended.", MessageStatus.GAME_ENDED);
            }
            game.nextPlayer();

            sendPrivateUpdates();
            return new Response("Cards moved", MessageStatus.OK);
        } else {
            System.out.println("Column does not have enough space");
            return buildInvalidResponse();
        }
    }

    /**
     * method called when a DeleteLimboRequest is received from a client
     *
     * @param deleteLimboRequest is the message received
     * @return the response to the message
     */
    private Response deleteLimboHandler(DeleteLimboRequest deleteLimboRequest) {
        if (deleteLimboRequest.getContent() == MessageContent.DELETE_LIMBO) {
            Server.LOGGER.log(Level.INFO, "Deleting limbo for player: {0}", deleteLimboRequest.getSenderUsername());
            for (Coordinate coordinate : game.getLimbo().keySet()) {
                game.getBoard().getGrid().put(coordinate, game.getLimbo().get(coordinate));
            }
            game.getLimbo().clear();

            sendPrivateUpdates();
            return new Response("Limbo deleted", MessageStatus.OK);
        } else {
            System.out.println("Limbo is not valid");
            return buildInvalidResponse();
        }
    }

    /**
     * method called when a ReorderLimboRequest is received from a client
     *
     * @param reorderLimboRequest is the message received
     * @return the response to the message
     */
    private Response reorderLimboHandler(ReorderLimboRequest reorderLimboRequest) {
        List<ObjectCard> limboOrder = reorderLimboRequest.getLimboOrder();

        if (reorderLimboRequest.getContent() == MessageContent.REORDER_LIMBO_REQUEST && limboOrder.size() == game.getLimbo().size()) {
            Server.LOGGER.log(Level.INFO, "Reordering limbo for player: {0}", reorderLimboRequest.getSenderUsername());
            game.setLimboOrder(limboOrder);

            return new Response("Limbo reordered", MessageStatus.PRINT_LIMBO);
        } else {
            System.out.println("Limbo order is not valid");
            return buildInvalidResponse();
        }
    }

    //TODO qui non ritorniamo una ObjectCardResponse ma una generica Response, eliminaiamo ObjectCardResponse?
    private Response pickObjectCardHandler(ObjectCardRequest objectCardRequest) {
        Coordinate c = objectCardRequest.getCoordinate();

        if (objectCardRequest.getContent() == MessageContent.PICK_OBJECT_CARD && c != null && isObjectCardAvailable(c)) {
            Server.LOGGER.log(Level.INFO, "Coordinate of the card: {0}", c);
            // TODO cambiare metodo con pick object card
            this.getGame().getLimbo().put(c, this.getGame().getBoard().removeObjectCard(c));

            sendPrivateUpdates();
            return new Response("Valid card!", MessageStatus.PRINT_LIMBO);
//            return new ObjectCardResponse(objectCardRequest.getSenderUsername());
        } else {
            System.out.println("Carta non valida");
            return buildInvalidResponse();
        }
    }

    /**
     * method called when a message is received from a client before the game starts
     * the message can be a request to add a player to the lobby or a request to set the number of players
     *
     * @param receivedMessage is the message received
     * @return the response to the message
     */
    private Message firstStateHandler(Message receivedMessage) {
        Server.LOGGER.log(Level.SEVERE, "FIRST STATE HANDLER: {0}", receivedMessage);
        switch (receivedMessage.getContent()) {
            case ADD_PLAYER:
                return lobbyMessageHandler((LobbyMessage) receivedMessage);
            case NUMBER_OF_PLAYERS:
                return numberOfPlayersMessageHandler((NumberOfPlayersMessage) receivedMessage);
            default:
                return buildInvalidResponse();
        }
    }

    /**
     * method called when a NumberOfPlayersMessage is received from a client
     *
     * @param numberOfPlayersMessage is the number of players entered by the client
     * @return the response to the message
     */
    private Response numberOfPlayersMessageHandler(NumberOfPlayersMessage numberOfPlayersMessage) {
        int numberOfPlayers = numberOfPlayersMessage.getNumberOfPlayers();

        if (numberOfPlayersMessage.getContent() == MessageContent.NUMBER_OF_PLAYERS && numberOfPlayers >= JsonReader.getMinPlayers() && numberOfPlayers <= JsonReader.getMaxPlayers()) {
            this.game.setNumberOfPlayers(numberOfPlayersMessage.getNumberOfPlayers());
            fillBoard();

            Server.LOGGER.log(Level.INFO, "Number of players for the game: {0}", numberOfPlayersMessage.getNumberOfPlayers());
        } else {
            return buildInvalidResponse();
        }

        return new Response("Number of players set", MessageStatus.OK);
    }

    /**
     * method called when a LobbyMessage is received from a client
     *
     * @param lobbyMessage is the message received
     * @return the response to the message
     */
    private Response lobbyMessageHandler(LobbyMessage lobbyMessage) {
        List<Player> inLobbyPlayers = game.getPlayers();

        if (lobbyMessage.getContent() == MessageContent.ADD_PLAYER && isUsernameAvailable(lobbyMessage.getSenderUsername()) && !lobbyMessage.isDisconnection()) {
            if (inLobbyPlayers.size() < JsonReader.getMaxPlayers()) {
                game.addPlayer(new Player(lobbyMessage.getSenderUsername(), new Shelf(), game.getRandomAvailablePersonalGoalCard()));

                server.sendMessageToAll(new LobbyPlayersResponse(new ArrayList<>(inLobbyPlayers.stream().map(player -> player.getName()).collect(Collectors.toList()))));
                Server.LOGGER.log(Level.INFO, "{0} joined the lobby", lobbyMessage.getSenderUsername());
            } else {
                return buildInvalidResponse();
            }
        } else {
            return buildInvalidResponse();
        }

        return checkLobby();
    }

    /**
     * build an invalid response
     *
     * @return a Response message to the client
     */
    private Response buildInvalidResponse() {
        return new Response("Invalid message", MessageStatus.ERROR);
    }

    /**
     * @return {@code true} if the lobby is full, otherwise false
     */
    public boolean getIsLobbyFull() {
        return isLobbyFull;
    }

    /**
     * @param lobbyFull tells if the lobby is full or not
     */
    public void setIsLobbyFull(boolean lobbyFull) {
        this.isLobbyFull = lobbyFull;
    }

    /**
     * @return the gameState
     */
    public PossibleGameState getGameState() {
        return gameState;
    }

    /**
     * @return the game
     */
    // TODO potrebbe essere un getInstance?
    public Game getGame() {
        return game;
    }

    /**
     * check if the lobby if full, if it is, it the game starts, otherwise it adds the player to the lobby
     *
     * @return a Response message to the client
     */
    private Response checkLobby() {
        List<Player> inLobbyPlayers = game.getPlayers();

        if (inLobbyPlayers.size() == this.game.getNumberOfPlayers()) {
            this.isLobbyFull = true;
            return new Response("Last player added, game is starting...", MessageStatus.OK);
        } else {
            return new Response("Player added to the game", MessageStatus.OK);
        }
    }

    public void gameSetupHandler() {
        if (game.getPlayers().size() == game.getNumberOfPlayers()) {
            game.setCurrentPlayer(game.getPlayers().get(0));
            startingStateHandler();
        }
    }

    /**
     * changes the state of the game
     *
     * @param changeState the new state of the game
     */
    void changeState(PossibleGameState changeState) {
        gameState = changeState;
    }

    private void startingStateHandler() {
        this.turnController = new TurnController(this.game.getPlayers(), this);
        changeState(PossibleGameState.GAME_STARTED);

        //non ci serve, abbiamp gia il current player
//        UserPlayer firstPlayer = roundController.getTurnManager().getTurnOwner();

        // TODO cosi riceve prima lo stato del game poi stampa inizio partita
//        sendPrivateUpdates();

        List<Player> players = game.getPlayers();

        for (Player player : players) {
            server.sendMessage(player.getName(), new GameStartMessage(game.getCurrentPlayer().getName(), game.getCommonGoals(), player.getName()));
        }
//        server.sendMessageToAll(new GameStartMessage(game.getCurrentPlayer().getName(), game.getCommonGoals(), player.getName()));
    }

    /**
     * This method sends to all clients the new state of the {@link Game Game}, contained in the
     * {@link GameSerialized GameSerialized}. This method is used to send an update of the
     * {@link Game Game} everytime that a normal action is completed
     */
    public void sendPrivateUpdates() {
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            server.sendMessage(player.getName(), new GameStateResponse(player.getName(), game.getCurrentPlayer().getName()));
        }
    }

    // TODO finire di implementare la response lato cli

    /**
     * this method sends to all clients an EndGameMessage, containing the Game Serialized
     */
    public void sendEndGame() {
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            server.sendMessage(player.getName(), new EndGameMessage(player.getName()));
        }
    }


    /**
     * Check if the username is available
     *
     * @param username is the username of the player
     * @return true if available, false if not
     * @throws NullPointerException if username is null
     */
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
    // TODO: non sarebbe meglio spostarlo nel model?
    public void fillBoard() {
        int playerNumber = game.getNumberOfPlayers();

        Map<Coordinate, ObjectCard> b = game.getBoard().getGrid();

        int[][] boardMatrix = JsonReader.getBoard(playerNumber);

        for (int i = 0; i < boardMatrix.length / 2; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1) {
                    b.put(new Coordinate(4 - i, j - 4), game.getRandomAvailableObjectCard());
                }
            }
        }

        for (int i = boardMatrix.length / 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1) {
                    b.put(new Coordinate(4 - i, j - 4), game.getRandomAvailableObjectCard());
                }
            }
        }
    }

    /**
     * Load the shelf with the ObjectCard, the order has already been established
     *
     * @param column      is the number of the column where the ObjectCard is added
     * @param objectCards is the ObjectCard to add in the current player's shelf
     */
    public void loadShelf(List<ObjectCard> objectCards, int column) {
        game.addObjectCardsToShelf(objectCards, column);
    }

    //si puo fare una modifica che non rimuova la coordinata della cella ma setti il contenuto a null


    /**
     * Checks if the object card at the given coordinate is available for selection.
     * An object card is considered available if it has at least one free side and,
     * when other object cards have been selected, it shares a side with one of them.
     * This method is used to determine if a player can pick up an object card from the board.
     * When three cards have been selected, the list of selected coordinates is cleared.
     *
     * @param coordinate The coordinate of the object card to check.
     * @return True if the object card is available for selection, false otherwise.
     */
    //TODO: da testare
    // non funziona il controllo per verificare che le tessere abbiano un lato in comune
    public boolean isObjectCardAvailable(Coordinate coordinate) {
        if (selectedCoordinates.size() == 3) {
            selectedCoordinates.clear();
        }
        if (selectedCoordinates.isEmpty()) {
            this.selectedCoordinates.add(coordinate);
            return this.game.getBoard().isEmptyAtDirection(coordinate, UP) ||
                    this.game.getBoard().isEmptyAtDirection(coordinate, DOWN) ||
                    this.game.getBoard().isEmptyAtDirection(coordinate, RIGHT) ||
                    this.game.getBoard().isEmptyAtDirection(coordinate, LEFT);
        } else {
            boolean hasFreeSide = this.game.getBoard().isEmptyAtDirection(coordinate, UP) ||
                    this.game.getBoard().isEmptyAtDirection(coordinate, DOWN) ||
                    this.game.getBoard().isEmptyAtDirection(coordinate, RIGHT) ||
                    this.game.getBoard().isEmptyAtDirection(coordinate, LEFT);
            boolean hasCommonSide = false;
            for (Coordinate selectedCoordinate : selectedCoordinates) {
                if (coordinate.getAdjacent(Coordinate.Direction.UP).equals(selectedCoordinate) ||
                        coordinate.getAdjacent(Coordinate.Direction.DOWN).equals(selectedCoordinate) ||
                        coordinate.getAdjacent(Coordinate.Direction.LEFT).equals(selectedCoordinate) ||
                        coordinate.getAdjacent(Coordinate.Direction.RIGHT).equals(selectedCoordinate)) {
                    hasCommonSide = true;
                    break;
                }
            }
            this.selectedCoordinates.add(coordinate);
            return hasFreeSide && hasCommonSide;
        }
    }
    /*
    Questo è il metodo originale, quello sopra è il metodo aggiornato
    public boolean isObjectCardAvailable(Coordinate coordinate) {
        return this.game.getBoard().isEmptyAtDirection(coordinate, UP) || this.game.getBoard().isEmptyAtDirection(coordinate, DOWN) || this.game.getBoard().isEmptyAtDirection(coordinate, RIGHT) || this.game.getBoard().isEmptyAtDirection(coordinate, LEFT);
    }

     */

    /**
     * Adds the object card at the specified coordinate to the limbo area.
     * The limbo area is used to store object cards that a player has picked up but not yet placed on their shelf.
     *
     * @param card The object card to add to the limbo area.
     * @throws NullPointerException If the object card is null (should not happen).
     */
    // TODO aggiustare metodi sapendo che il limbo è una mappa coordinata-carta per un eventuale annullamento
    // limbo e reinserimento nella board
    public boolean addObjectCardToLimbo(ObjectCard card) throws NullPointerException {
        if (card == null) throw new NullPointerException("ObjectCard is null");
        if (this.getGame().getLimbo().size() == 3) return false;

//        this.getGame().getLimbo().add(card);
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
     * this method is used to calculate the winner of the game at the end of it
     *
     * @return the name of the winner
     */
    private void calculateWinner() {
        int maxPoints = 0;
//        String winner = "";
        for (Player p : this.game.getPlayers()) {
            if (p.getCurrentPoints() > maxPoints) {
                maxPoints = p.getCurrentPoints();
//                winner = p.getName();
            }
        }

        for (Player p : this.game.getPlayers()) {
            if (p.getCurrentPoints() == maxPoints) {
                p.setWinner(true);
            }
        }
    }

    @Override
    public void onTimerRun() {
        // TODO
    }

    @Override
    public String toString() {
        return "ControllerGame{" +
                "id=" + id +
                '}';
    }
}

