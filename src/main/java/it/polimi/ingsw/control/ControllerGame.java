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
import java.util.*;
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
    private Timer reconnectionTimer;

    /**
     * Constructor for the ControllerGame class, initializing the game state.
     */
    public ControllerGame(Server server) {
        JsonReader.readJsonConstant("GameConstant.json");
        this.server = server;
        this.id = UUID.randomUUID();
        this.selectedCoordinates = new ArrayList<>();
        this.game = null;
    }

    /**
     * Method used to get the keys of a map as an ArrayList
     *
     * @param map is the map
     * @return the ArrayList of the keys of the map
     */
    public static ArrayList<Coordinate> getKeysAsArrayList(Map<Coordinate, ObjectCard> map) {
        ArrayList<Coordinate> keys = new ArrayList<>();
        for (Map.Entry<Coordinate, ObjectCard> entry : map.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    /**
     * Method used to reorder a list
     *
     * @param list1 is the list to reorder
     * @param list2 is the list with the new order
     */
    public static void reorderList(List<Coordinate> list1, List<Integer> list2) {
        List<Coordinate> tempList = new ArrayList<>(list1);

        for (int i = 0; i < list2.size(); i++) {
            int index = list2.get(i);
            if (index >= 0 && index < tempList.size()) {
                Coordinate element = tempList.get(index);
                list1.set(i, element);
            }
        }
    }

    /**
     * Method use to
     *
     * @param map   is the map to reorder
     * @param order is the list with the new order
     * @return the reordered map
     */
    public static LinkedHashMap<Coordinate, ObjectCard> reorderMap(LinkedHashMap<Coordinate, ObjectCard> map, List<Coordinate> order) {
        LinkedHashMap<Coordinate, ObjectCard> orderedMap = new LinkedHashMap<>();

        for (Coordinate coordinate : order) {
            if (map.containsKey(coordinate)) {
                // if the original map contains the coordinate, put it in the new map
                ObjectCard objectCard = map.get(coordinate);
//                System.out.println(orderedMap);
                orderedMap.put(coordinate, objectCard);
            }
        }
        System.out.println("ordered map " + orderedMap);
        return orderedMap;
    }

    /**
     * @return the id of the game
     */
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

//        System.out.println(gameState);

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
                return new GameStateResponse(receivedMessage.getSenderUsername(), game.getCurrentPlayer().getName(), server.getFilepath());
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
    protected Response loadShelfHandler(LoadShelfRequest LoadShelfRequest) {
        int col = LoadShelfRequest.getColumn();

        if (LoadShelfRequest.getContent() == MessageContent.LOAD_SHELF_REQUEST && game.getCurrentPlayer().getShelf().getFreeCellsPerColumn(col) >= game.getLimbo().size()) {
            Server.LOGGER.log(Level.INFO, "Moving cards to shelf for player: {0}", LoadShelfRequest.getSenderUsername());
            loadShelf(new ArrayList<>(game.getLimbo().values()), col);

            this.pointsCalculator();

//           turnController.nextTurn();
            game.getLimbo().clear();
            if (game.getCurrentPlayer().getShelf().isFull()) {
                // send a private message to all the player containing the winner
                calculateWinner();
                game.setStarted(false);
                sendEndGame();
                changeState(PossibleGameState.GAME_ENDED);
                return new Response("Game has ended.", MessageStatus.GAME_ENDED);
            }

            Player currentPlayer = game.getCurrentPlayer();
            Player nextPlayer = game.nextPlayer();

            if (nextPlayer == currentPlayer) {
                setTimer();
            }
//            turnController.setActivePlayer(game.getCurrentPlayer());

            sendPrivateUpdates();

            if (checkIfRefill()) refillBoard();
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
        ArrayList<Integer> newLimboOrder = reorderLimboRequest.getLimboOrder();
        ArrayList<Coordinate> limboCoordinates;

        if (reorderLimboRequest.getContent() == MessageContent.REORDER_LIMBO_REQUEST && newLimboOrder.size() == game.getLimbo().size()) {
            Server.LOGGER.log(Level.INFO, "Reordering limbo for player: {0}", reorderLimboRequest.getSenderUsername());

            limboCoordinates = getKeysAsArrayList(game.getLimbo());
            reorderList(limboCoordinates, newLimboOrder);
            LinkedHashMap<Coordinate, ObjectCard> newLimbo = reorderMap(game.getLimbo(), limboCoordinates);
            game.setLimbo(newLimbo);
            server.sendMessage(reorderLimboRequest.getSenderUsername(), new GameStateResponse(reorderLimboRequest.getSenderUsername(), game.getCurrentPlayer().getName(), server.getFilepath()));
            //TODO si puo mandare un altro game state message cosi il limbo viene aggiornato amche lato client
            return new Response("Limbo reordered", MessageStatus.PRINT_LIMBO);
        } else {
            System.out.println("Limbo order is not valid");
            return buildInvalidResponse();
        }
    }

    /**
     * method called when a ObjectCardRequest is received from a client, asking to pick an object card
     *
     * @param objectCardRequest is the message received
     * @return the response to the message
     */
    //TODO qui non ritorniamo una ObjectCardResponse ma una generica Response, eliminaiamo ObjectCardResponse?
    private Response pickObjectCardHandler(ObjectCardRequest objectCardRequest) {
        Coordinate c = objectCardRequest.getCoordinate();

        if (objectCardRequest.getContent() == MessageContent.PICK_OBJECT_CARD && c != null && isObjectCardAvailable(c)) {
            Server.LOGGER.log(Level.INFO, "Coordinate of the card: {0}", c);
            // TODO cambiare metodo con pick object card

            this.getGame().getLimbo().put(c, this.getGame().getBoard().removeObjectCard(c));
            System.out.println(this.getGame().getLimbo());
            sendPrivateUpdates();
            return new Response("Valid card!", MessageStatus.PRINT_LIMBO);
//            return new ObjectCardResponse(objectCardRequest.getSenderUsername());
        } else {
            System.out.println("Carta non valida");
            return new Response("Carta non valida", MessageStatus.NOT_VALID_CARD);
//            return buildInvalidResponse();
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
            this.game.setGameName(numberOfPlayersMessage.getGameName());
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
        if (game == null) this.game = Game.getInstance(lobbyMessage.getSenderUsername());
        List<Player> inLobbyPlayers = game.getPlayers();

        if (lobbyMessage.getContent() == MessageContent.ADD_PLAYER && isUsernameAvailable(lobbyMessage.getSenderUsername()) && !lobbyMessage.isDisconnection()) {
            if (inLobbyPlayers.size() < JsonReader.getMaxPlayers()) {
                game.addPlayer(new Player(lobbyMessage.getSenderUsername(), new Shelf(), game.getRandomAvailablePersonalGoalCard()));
                server.getPlayersGame().put(lobbyMessage.getSenderUsername(), this);
                Game.getInstanceMap().put(lobbyMessage.getSenderUsername(), game);


                Server.LOGGER.log(Level.INFO, "{0} joined the lobby", lobbyMessage.getSenderUsername());
                System.out.println("Players in lobby: " + game.getPlayers());

                server.sendMessageToAll(this.id, new LobbyPlayersResponse(new ArrayList<>(inLobbyPlayers.stream().map(Player::getName).collect(Collectors.toList()))));
            } else {
                return buildInvalidResponse();
            }
        } else {
            return buildInvalidResponse();
        }

        return checkLobby();
    }

    /**
     * check if the lobby is full, in that case the game starts
     */
    public void gameSetupHandler() {
        if (game.getPlayers().size() == game.getNumberOfPlayers()) {
            setIsLobbyFull(true);
            game.setCurrentPlayer(game.getPlayers().get(0));
            startingStateHandler();
        }
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

    public void setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the game
     */
    // TODO potrebbe essere un getInstance?
    public Game getGame() {
        return game;
    }

    /**
     * set the game
     *
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
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

    /**
     * changes the state of the game
     *
     * @param changeState the new state of the game
     */
    void changeState(PossibleGameState changeState) {
        gameState = changeState;
    }

    /**
     * method called when the game starts, it creates a new {@link TurnController TurnController} and
     * sends to all clients the new state of the game
     */
    private void startingStateHandler() {
        this.turnController = new TurnController(this.game.getPlayers(), this);
        turnController.setActivePlayer(game.getCurrentPlayer());
        changeState(PossibleGameState.GAME_STARTED);
        game.setStarted(true);

        //non ci serve, abbiamp gia il current player
//        UserPlayer firstPlayer = roundController.getTurnManager().getTurnOwner();

        // TODO cosi riceve prima lo stato del game poi stampa inizio partita
//        sendPrivateUpdates();

        List<Player> players = game.getPlayers();

        for (Player player : players) {
            server.sendMessage(player.getName(), new GameStartMessage(game.getCurrentPlayer().getName(), game.getCommonGoals(), player.getName(), server.getFilepath()));
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
            server.sendMessage(player.getName(), new GameStateResponse(player.getName(), game.getCurrentPlayer().getName(), server.getFilepath()));
        }
    }

    /**
     * this method sends to all clients an EndGameMessage, containing the Game Serialized
     */
    public void sendEndGame() {
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            server.sendMessage(player.getName(), new EndGameMessage(player.getName(), server.getFilepath()));
            synchronized (server.getClientsLock()) {
                server.getClients().remove(player.getName());
                server.getPlayersGame().remove(player.getName());
            }
        }
        server.getControllerGames().remove(this);
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

    public boolean checkIfRefill() {
        int playerNumber = game.getNumberOfPlayers();
        Map<Coordinate, ObjectCard> b = game.getBoard().getGrid();
        int[][] boardMatrix = JsonReader.getBoard(playerNumber);

        if (game.getBoard().getGrid().size() == 0) return true;

        for (int i = 1; i < boardMatrix.length / 2; i++) {
            for (int j = 1; j < boardMatrix[i].length - 1; j++) {
                if (b.get(new Coordinate(4 - i, j - 4)) != null) {
                    if (boardMatrix[i + 1][j] == 1 && b.get(new Coordinate(4 - i + 1, j - 4)) != null) {
                        return false;
                    }
                    if (boardMatrix[i - 1][j] == 1 && b.get(new Coordinate(4 - i - 1, j - 4)) != null) {
                        return false;
                    }
                    if (boardMatrix[i][j + 1] == 1 && b.get(new Coordinate(4 - i, j - 4 + 1)) != null) {
                        return false;
                    }
                    if (boardMatrix[i][j - 1] == 1 && b.get(new Coordinate(4 - i, j - 4 + 1)) != null) {
                        return false;
                    }
                }
            }
        }

        for (int i = boardMatrix.length / 2; i < boardMatrix.length - 1; i++) {
            for (int j = 1; j < boardMatrix[i].length - 1; j++) {
                if (b.get(new Coordinate(4 - i, j - 4)) != null) {
                    if (boardMatrix[i + 1][j] == 1 && b.get(new Coordinate(4 - i + 1, j - 4)) != null) {
                        return false;
                    }
                    if (boardMatrix[i - 1][j] == 1 && b.get(new Coordinate(4 - i - 1, j - 4)) != null) {
                        return false;
                    }
                    if (boardMatrix[i][j + 1] == 1 && b.get(new Coordinate(4 - i, j - 4 + 1)) != null) {
                        return false;
                    }
                    if (boardMatrix[i][j - 1] == 1 && b.get(new Coordinate(4 - i, j - 4 + 1)) != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Fills the game board again with object cards based on the number of players.
     * This method should be called at the beginning of the game to set up the board.
     */
    public void refillBoard() {
        int playerNumber = game.getNumberOfPlayers();

        Map<Coordinate, ObjectCard> b = game.getBoard().getGrid();

        int[][] boardMatrix = JsonReader.getBoard(playerNumber);

        for (int i = 0; i < boardMatrix.length / 2; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1 && b.get(new Coordinate(4 - i, j - 4)) == null) {
                    b.put(new Coordinate(4 - i, j - 4), game.getRandomAvailableObjectCard());
                }
            }
        }

        for (int i = boardMatrix.length / 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1 && b.get(new Coordinate(4 - i, j - 4)) == null) {
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
//    public boolean isObjectCardAvailable(Coordinate coordinate) {
//        if (selectedCoordinates.size() == 3) {
//            selectedCoordinates.clear();
//        }
//        if (selectedCoordinates.isEmpty()) {
//            this.selectedCoordinates.add(coordinate);
//            return this.game.getBoard().isEmptyAtDirection(coordinate, UP) ||
//                    this.game.getBoard().isEmptyAtDirection(coordinate, DOWN) ||
//                    this.game.getBoard().isEmptyAtDirection(coordinate, RIGHT) ||
//                    this.game.getBoard().isEmptyAtDirection(coordinate, LEFT);
//        } else {
//            boolean hasFreeSide = this.game.getBoard().isEmptyAtDirection(coordinate, UP) ||
//                    this.game.getBoard().isEmptyAtDirection(coordinate, DOWN) ||
//                    this.game.getBoard().isEmptyAtDirection(coordinate, RIGHT) ||
//                    this.game.getBoard().isEmptyAtDirection(coordinate, LEFT);
//            boolean hasCommonSide = false;
//            for (Coordinate selectedCoordinate : selectedCoordinates) {
//                if (coordinate.getAdjacent(Coordinate.Direction.UP).equals(selectedCoordinate) ||
//                        coordinate.getAdjacent(Coordinate.Direction.DOWN).equals(selectedCoordinate) ||
//                        coordinate.getAdjacent(Coordinate.Direction.LEFT).equals(selectedCoordinate) ||
//                        coordinate.getAdjacent(Coordinate.Direction.RIGHT).equals(selectedCoordinate)) {
//                    hasCommonSide = true;
//                    break;
//                }
//            }
//            this.selectedCoordinates.add(coordinate);
//            return hasFreeSide && hasCommonSide;
//        }
//    }
    public boolean isObjectCardAvailable(Coordinate coordinate) {
        Map<Coordinate, ObjectCard> limbo = game.getLimbo();
        Map<Coordinate, ObjectCard> boardOrig = game.getBoard().getGrid();
        Iterator<Coordinate> iterator = limbo.keySet().iterator();
        boolean available = false;

        Board board = new Board();
        Map<Coordinate, ObjectCard> grid = new HashMap<>(boardOrig);
        limbo.forEach(grid::put);
        board.setGrid(grid);


        if (!grid.containsKey(coordinate))
            return false;

        if (!board.isEmptyAtDirection(coordinate, UP) &&
                !board.isEmptyAtDirection(coordinate, DOWN) &&
                !board.isEmptyAtDirection(coordinate, RIGHT) &&
                !board.isEmptyAtDirection(coordinate, LEFT)) return false;

        if (limbo.size() == 0) available = true;

        if (limbo.size() == 1) {
            Coordinate c = iterator.next();
            int dx = Math.abs(c.getColumn() - coordinate.getColumn());
            int dy = Math.abs(c.getRow() - coordinate.getRow());

            if ((dx == 0 && dy == 1) || (dy == 0 && dx == 1))
                available = true;
            else return false;
        }

        if (limbo.size() == 2) {
            Coordinate c1 = iterator.next();
            Coordinate c2 = iterator.next();

            int dx1 = Math.abs(c1.getColumn() - coordinate.getColumn());
            int dy1 = Math.abs(c1.getRow() - coordinate.getRow());

            int dx2 = Math.abs(c2.getColumn() - coordinate.getColumn());
            int dy2 = Math.abs(c2.getRow() - coordinate.getRow());

            // Check if coordinate is close to either of the limbo cards
            if ((dx1 == 0 && dy1 == 1) || (dy1 == 0 && dx1 == 1) ||
                    (dx2 == 0 && dy2 == 1) || (dy2 == 0 && dx2 == 1)) {

                // Check if coordinate forms a straight line with the limbo cards
                if ((c1.getColumn() == c2.getColumn() && coordinate.getColumn() == c1.getColumn()) ||
                        (c1.getRow() == c2.getRow() && coordinate.getRow() == c1.getRow())) {
                    available = true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return available;
    }

//    public boolean isObjectCardAvailable(Coordinate coordinate) {
//        Map<Coordinate, ObjectCard> limbo = game.getLimbo();
//        Map<Coordinate, ObjectCard> board = game.getBoard().getGrid();
//        Iterator<Coordinate> iterator = limbo.keySet().iterator();
//        boolean available = false;
//
//        if (!board.containsKey(coordinate))
//            return false;
//
//        if (!this.game.getBoard().isEmptyAtDirection(coordinate, UP) &&
//                !this.game.getBoard().isEmptyAtDirection(coordinate, DOWN) &&
//                !this.game.getBoard().isEmptyAtDirection(coordinate, RIGHT) &&
//                !this.game.getBoard().isEmptyAtDirection(coordinate, LEFT)) return false;
//
//        if (limbo.size() == 0) available = true;
//
//        if (limbo.size() == 1) {
//            Coordinate c = iterator.next();
//            int dx = Math.abs(c.getColumn() - coordinate.getColumn());
//            int dy = Math.abs(c.getRow() - coordinate.getRow());
//
//            if ((dx == 0 && dy == 1) || (dy == 0 && dx == 1))
//                available = true;
//            else return false;
//        }
//
//        if (limbo.size() == 2) {
//            Coordinate c1 = iterator.next();
//            Coordinate c2 = iterator.next();
//            int dx = Math.abs(c1.getColumn() - c2.getColumn());
//            int dy = Math.abs(c1.getRow() - c2.getRow());
//
//            if ((dx == 0 && dy == 1) || (dy == 0 && dx == 1))
//                available = true;
//            else return false;
//
//            dx = Math.abs(c2.getColumn() - coordinate.getColumn());
//            dy = Math.abs(c2.getRow() - coordinate.getRow());
//
//            if ((dx == 0 && dy == 1) || (dy == 0 && dx == 1))
//                available = true;
//            else return false;
//        }
//
//        return available;
//    }


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
//    public boolean addObjectCardToLimbo(ObjectCard card) throws NullPointerException {
//        if (card == null) throw new NullPointerException("ObjectCard is null");
//        if (this.getGame().getLimbo().size() == 3) return false;
//
////        this.getGame().getLimbo().add(card);
//        return true;
//    }

    /**
     * pick the ObjectCard from the board
     *
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return the ObjectCard with that Coordinate
     */
//    public ObjectCard pickObjectCard(Coordinate coordinate) {
//        if (isObjectCardAvailable(coordinate)) {
//            return this.game.getBoard().removeObjectCard(coordinate);
//        } else return null;
//    }

    /**
     * Calculate the points of the currentPlayer. Each time the method counts the points starting from 0.
     * Then set the points to the currentPlayer.
     *
     * @return the point of the currentPlayer
     */
    public int pointsCalculator() {
        int points = 0;

        points += this.game.getCurrentPlayer().getPersonalGoalCard().calculatePoints();
        System.out.println("PUNTI PERSONAL GOAL: " + points);

        for (CommonGoal c : this.game.getCommonGoals()) {
            if (c.checkGoal(this.game.getCurrentPlayer().getShelf())) {
                points += c.updateCurrentPoints(this.game.getPlayers().size());
            }
        }
        System.out.println("PUNTI COMMON GOALS: " + points);

        points += this.game.getCurrentPlayer().getShelf().closeObjectCardsPoints();
        System.out.println("PUNTI CARTE OGGETTO VICINE: " + points);

        if (this.game.getCurrentPlayer().getShelf().isFull()) points++;
        System.out.println("PUNTI SHELF PIENA: " + points);

        this.game.getCurrentPlayer().setCurrentPoints(points);

        return points;
    }

    /**
     * this method is used to calculate the winner of the game at the end of it, setting the winner attribute of the
     * {@link Player player} who has the highest score to true
     */
    public void calculateWinner() {
        int maxPoints = 0;
        for (Player p : this.game.getPlayers()) {
            if (p.getCurrentPoints() > maxPoints) {
                maxPoints = p.getCurrentPoints();
            }
        }

        for (Player p : this.game.getPlayers()) {
            if (p.getCurrentPoints() == maxPoints) {
                p.setWinner(true);
            }
        }
    }

    /**
     * Sub method of the class only used while during the game the {@link Server server} receives disconnection messages from
     * the {@link Player userPLayers} in the game
     *
     * @param receivedConnectionMessage Message received by the server from a connecting or disconnecting {@link Player UserPlayer}
     * @return a {@link Message Message} which contains the result of the received message
     */
    public Message onConnectionMessage(Message receivedConnectionMessage) {
        if (gameState == PossibleGameState.GAME_ENDED) {
            return new Response("GAME ENDED", MessageStatus.ERROR);
        }

//        if (!InputValidator.validatePlayerUsername(game.getPlayers(), receivedConnectionMessage)) {
//            return new Response("Invalid connection Message", MessageStatus.ERROR);
//        }

        if (gameState != PossibleGameState.GAME_ROOM && receivedConnectionMessage.getContent() == MessageContent.ADD_PLAYER) {
            // if the player wants to disconnect from the game
            if (((LobbyMessage) receivedConnectionMessage).isDisconnection()) {
//                return disconnectionHandler((LobbyMessage) receivedConnectionMessage);
                // if the player wants to reconnect to the game
            } else {
                return reconnectionHandler((LobbyMessage) receivedConnectionMessage);
            }
        }

        System.out.println("Invalid game state");
        return buildInvalidResponse();
    }

//    public Message onConnectionMessage(Message receivedConnectionMessage) {
//        if (gameState == PossibleGameState.GAME_ENDED) {
//            return new Response("GAME ENDED", MessageStatus.ERROR);
//        }
//
////        if (!InputValidator.validatePlayerUsername(game.getPlayers(), receivedConnectionMessage)) {
////            return new Response("Invalid connection Message", MessageStatus.ERROR);
////        }
//
//        if (gameState != PossibleGameState.GAME_ROOM && receivedConnectionMessage.getContent() == MessageContent.GET_IN_LOBBY) {
//            if (((LobbyMessage) receivedConnectionMessage).isDisconnection()) {
//                return disconnectionHandler((LobbyMessage) receivedConnectionMessage);
//            } else {
//                return reconnectionHandler((LobbyMessage) receivedConnectionMessage);
//            }
//        } else {
//            System.out.println("Invalid game state");
//            return null;
//        }
//    }
//

    /**
     * Method used to handle the reconnection of a player from the game
     *
     * @param receivedConnectionMessage message received by the server from the player asking to reconnect to the game
     * @return a {@link Message Message} which contains the result of the received message
     */
    private Message reconnectionHandler(LobbyMessage receivedConnectionMessage) {
        String reconnectingPlayerName = receivedConnectionMessage.getSenderUsername();
        List<String> playersNames = game.getPlayers().stream().map(Player::getName).collect(Collectors.toList());
//        ArrayList<LobbyMessage> inLobbyPlayers = lobby.getInLobbyPlayers();

        if (!game.isStarted()) {
            return new Response("Game is ended.", MessageStatus.ERROR);
        }
        if (playersNames.contains(reconnectingPlayerName)) {
            if (reconnectionTimer != null) {
                reconnectionTimer.cancel();
                reconnectionTimer = null;
                game.setCurrentPlayer(game.getPlayerByName(reconnectingPlayerName));
            }
            // if I receive a reconnection message the player state change into connected == true
            game.getPlayerByName(reconnectingPlayerName).setConnected(true);

            server.sendMessageToAll(this.id, new ReconnectionMessage("Player " + reconnectingPlayerName + " reconnected to the game.", game.getCurrentPlayer().getName()));
//            return new ReconnectionMessage(receivedConnectionMessage.getToken(),
//                    new GameStateResponse(receivedConnectionMessage.getSenderUsername(),
//                            turnController.getActivePlayer().getName()));
        } else {
            return new Response("Reconnection message from already in lobby Player", MessageStatus.ERROR);
        }
//        return new GameStateResponse(reconnectingPlayerName, turnController.getActivePlayer().getName());
//        return new GameStateResponse(reconnectingPlayerName, game.getCurrentPlayer().getName());
        server.sendMessage(reconnectingPlayerName, new GameStateResponse(reconnectingPlayerName, game.getCurrentPlayer().getName(), server.getFilepath()));
        return new ReconnectionRequest("Reconnection request", receivedConnectionMessage.getToken());
    }


//    private Message disconnectionHandler(LobbyMessage receivedConnectionMessage) {
//        ArrayList<LobbyMessage> inLobbyPlayers = lobby.getInLobbyPlayers();
//        boolean gameEnded;
//
//        if (inLobbyPlayers.contains(receivedConnectionMessage)) {
//            // if I receive a disconnection message I remove it from the lobby and set the corresponding player state to DISCONNECTED
//            inLobbyPlayers.remove(receivedConnectionMessage);
//            ((Player) game.getPlayerByName(receivedConnectionMessage.getSenderUsername())).setPlayerState(PossiblePlayerState.DISCONNECTED);
//
//            // then I check if in the lobby there are still enough players to continue the game, if not the game ends
//            gameEnded = checkStartedLobby();
//
//            if (gameEnded) {
//                return new Response("Player disconnected, game has now less then 3 players and then is ending...", MessageStatus.OK);
//            }
//            // if game hasn't ended I check if the disconnected player is the turn owner, if so I change the state, otherwise nothing happens
//            else if (turnController.getActivePlayer().getName().equals(receivedConnectionMessage.getSenderUsername())) {
////                turnController.handlePassAction();
////                return new Response("Turn Owner disconnected, turn is passed to next Player", MessageStatus.OK);
//            } else {
//                return new Response("Player disconnected from the game", MessageStatus.OK);
//            }
//        } else {
//            return new Response("Disconnection Message from not in lobby Player", MessageStatus.ERROR);
//        }
//    }

    /**
     * Method that start a timer whenever only one player is in the game
     */
    public void setTimer() {
        reconnectionTimer = new Timer();

        // code to run when the timer ends
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                game.setStarted(false);
                game.getCurrentPlayer().setWinner(true);
                sendEndGame();
            }
        };

        // start a timer of 10 seconds (10000 milliseconds)
        reconnectionTimer.schedule(task, 5000);
    }

    @Override
    public void onTimerRun() {
        // TODO
    }

//    @Override
//    public String toString() {
//        return "ControllerGame{" +
//                "server=" + server +
//                ", id=" + id +
//                ", game=" + game +
//                ", selectedCoordinates=" + selectedCoordinates +
//                ", gameState=" + gameState +
//                ", isLobbyFull=" + isLobbyFull +
//                ", turnController=" + turnController +
//                '}';
//    }
}

