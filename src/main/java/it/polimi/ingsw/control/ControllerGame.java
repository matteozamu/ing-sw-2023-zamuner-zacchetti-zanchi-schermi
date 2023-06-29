package it.polimi.ingsw.control;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleGameState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.JsonReader;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.Board.Direction.*;

/**
 * Controller for the game, handling game logic and interactions between model components.
 */
public class ControllerGame implements Serializable {
    private final transient Server server;
    private UUID id;
    private Game game;
    private PossibleGameState gameState = PossibleGameState.GAME_ROOM;
    private boolean isLobbyFull;
    private Timer reconnectionTimer;
    private Timer makeMoveTimer;

    /**
     * Constructor for the ControllerGame class, initializing the game state.
     */
    public ControllerGame(Server server) {
        JsonReader.readJsonConstant(server.getFilepath());
        this.server = server;
        this.id = UUID.randomUUID();
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
     * Method used to reorder a map of coordinates and object cards
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
                orderedMap.put(coordinate, objectCard);
            }
        }
        return orderedMap;
    }

    /**
     * method that sets the timer for making a move in the game
     */

    public void setMakeMoveTimer() {
        makeMoveTimer = new Timer();

        // code to run when the timer ends
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                server.sendMessage(game.getCurrentPlayer().getName(), new Response("Timer ended", MessageStatus.QUIT));

                for (Coordinate coordinate : game.getLimbo().keySet()) {
                    game.getBoard().getGrid().put(coordinate, game.getLimbo().get(coordinate));
                }
                game.getLimbo().clear();

                game.nextPlayer();

                int connectionCounter = 0;
                for (Player pl : game.getPlayers()) {
                    if (pl.isConnected()) connectionCounter++;
                }
                if (connectionCounter == 1) {
                    setTimer();
                }

                sendPrivateUpdates();
            }
        };

        // start a timer of 5 minutes (300000 milliseconds) to let the player play
        makeMoveTimer.schedule(task, 300000);
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

        if (gameState == PossibleGameState.GAME_ROOM) {
            return firstStateHandler(receivedMessage);
        }

        if (gameState == PossibleGameState.GAME_ENDED) {
            return new Response("GAME ENDED", MessageStatus.ERROR);
        }

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
            game.getLimbo().clear();
            if (game.getCurrentPlayer().getShelf().isFull()) {
                // send a private message to all the player containing the winner
                calculateWinner();
                game.setStarted(false);
                sendEndGame();
                changeState(PossibleGameState.GAME_ENDED);
                return new Response("Game has ended.", MessageStatus.GAME_ENDED);
            }

            makeMoveTimer.cancel();

            Player currentPlayer = game.getCurrentPlayer();
            Player nextPlayer = game.nextPlayer();

            setMakeMoveTimer();
            if (nextPlayer == currentPlayer) {
                setTimer();
            }

            if (checkIfRefill()) refillBoard();

            sendPrivateUpdates();

            return new Response("Cards moved", MessageStatus.OK);
        } else {
            return buildInvalidResponse();
        }
    }

    /**
     * method called when a DeleteLimboRequest is received from a client
     *
     * @param deleteLimboRequest is the message received
     * @return the response to the message
     */
    protected Response deleteLimboHandler(DeleteLimboRequest deleteLimboRequest) {
        if (deleteLimboRequest.getContent() == MessageContent.DELETE_LIMBO) {
            Server.LOGGER.log(Level.INFO, "Deleting limbo for player: {0}", deleteLimboRequest.getSenderUsername());
            for (Coordinate coordinate : game.getLimbo().keySet()) {
                game.getBoard().getGrid().put(coordinate, game.getLimbo().get(coordinate));
            }
            game.getLimbo().clear();

            sendPrivateUpdates();
            return new Response("Limbo deleted", MessageStatus.OK);
        } else {
            return buildInvalidResponse();
        }
    }

    /**
     * method called when a ReorderLimboRequest is received from a client
     *
     * @param reorderLimboRequest is the message received
     * @return the response to the message
     */
    protected Response reorderLimboHandler(ReorderLimboRequest reorderLimboRequest) {
        ArrayList<Integer> newLimboOrder = reorderLimboRequest.getLimboOrder();
        ArrayList<Coordinate> limboCoordinates;

        if (reorderLimboRequest.getContent() == MessageContent.REORDER_LIMBO_REQUEST && newLimboOrder.size() == game.getLimbo().size()) {
            Server.LOGGER.log(Level.INFO, "Reordering limbo for player: {0}", reorderLimboRequest.getSenderUsername());

            limboCoordinates = getKeysAsArrayList(game.getLimbo());
            reorderList(limboCoordinates, newLimboOrder);
            LinkedHashMap<Coordinate, ObjectCard> newLimbo = reorderMap(game.getLimbo(), limboCoordinates);
            game.setLimbo(newLimbo);
            server.sendMessage(reorderLimboRequest.getSenderUsername(), new GameStateResponse(reorderLimboRequest.getSenderUsername(), game.getCurrentPlayer().getName(), server.getFilepath()));
            return new Response("Limbo reordered", MessageStatus.PRINT_LIMBO);
        } else {
            return buildInvalidResponse();
        }
    }

    /**
     * method called when a ObjectCardRequest is received from a client, asking to pick an object card
     *
     * @param objectCardRequest is the message received
     * @return the response to the message
     */
    protected Response pickObjectCardHandler(ObjectCardRequest objectCardRequest) {
        Coordinate c = objectCardRequest.getCoordinate();

        if (objectCardRequest.getContent() == MessageContent.PICK_OBJECT_CARD && c != null && isObjectCardAvailable(c)) {
            Server.LOGGER.log(Level.INFO, "Coordinate of the card: {0}", c);
            this.getGame().getLimbo().put(c, this.getGame().getBoard().removeObjectCard(c));
            sendPrivateUpdates();
            return new Response("Valid card :)", MessageStatus.PRINT_LIMBO);
        } else {
            return new Response("Invalid card :(", MessageStatus.NOT_VALID_CARD);
        }
    }

    /**
     * method called when a message is received from a client before the game starts
     * the message can be a request to add a player to the lobby or a request to set the number of players
     *
     * @param receivedMessage is the message received
     * @return the response to the message
     */
    protected Message firstStateHandler(Message receivedMessage) {
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
    protected Response numberOfPlayersMessageHandler(NumberOfPlayersMessage numberOfPlayersMessage) {
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
    protected Response lobbyMessageHandler(LobbyMessage lobbyMessage) {
        if (game == null) this.game = Game.getInstance(lobbyMessage.getSenderUsername());
        List<Player> inLobbyPlayers = game.getPlayers();

        if (lobbyMessage.getContent() == MessageContent.ADD_PLAYER && isUsernameAvailable(lobbyMessage.getSenderUsername()) && !lobbyMessage.isDisconnection()) {
            if (inLobbyPlayers.size() < JsonReader.getMaxPlayers()) {
                game.addPlayer(new Player(lobbyMessage.getSenderUsername(), new Shelf(), game.getRandomAvailablePersonalGoalCard()));
                server.getPlayersGame().put(lobbyMessage.getSenderUsername(), this);
                Game.getInstanceMap().put(lobbyMessage.getSenderUsername(), game);

                Server.LOGGER.log(Level.INFO, "{0} joined the lobby", lobbyMessage.getSenderUsername());

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

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Get the game
     *
     * @return the game
     */
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
     * check if the lobby is full, if it is, it the game starts, otherwise it adds the player to the lobby
     *
     * @return a Response message to the client
     */
    protected Response checkLobby() {
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
     * method called when the game starts, and
     * sends to all clients the new state of the game
     */
    private void startingStateHandler() {
        changeState(PossibleGameState.GAME_STARTED);
        game.setStarted(true);

        List<Player> players = game.getPlayers();

        for (Player player : players) {
            server.sendMessage(player.getName(), new GameStartMessage(game.getCurrentPlayer().getName(), game.getCommonGoals(), player.getName(), server.getFilepath()));
        }

        setMakeMoveTimer();
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

    /**
     * Check if the board needs to be refilled
     *
     * @return true if it needs to be refilled, false if not
     */

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

    /**
     * Check availability of the ObjectCard from the board
     *
     * @param coordinate are the board coordinates of the ObjectCard to check
     * @return true if the ObjectCard can be taken by a player, false if not
     */
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

    /**
     * Calculate the points of the currentPlayer. Each time the method counts the points starting from 0.
     * Then set the points to the currentPlayer.
     *
     * @return the point of the currentPlayer
     */
    public int pointsCalculator() {
        int points = 0;
        Player player = game.getCurrentPlayer();

        //check personal goal
        points += this.game.getCurrentPlayer().getPersonalGoalCard().calculatePoints(player.getShelf());

        //check common goals
        for (CommonGoal c : this.game.getCommonGoals()) {
            if (c.checkGoal(player.getShelf())) {
                if (!player.getCommonGoalsReached().containsKey(c)) {
                    int pointsToAdd = c.updateCurrentPoints(this.game.getPlayers().size());
                    points += pointsToAdd;
                    player.getCommonGoalsReached().put(c, pointsToAdd);
                } else {
                    points += player.getCommonGoalsReached().get(c);
                }
            }
        }

        //check near object cards
        points += player.getShelf().closeObjectCardsPoints();

        //check shelf fullness
        if (player.getShelf().isFull()) points++;

        player.setCurrentPoints(points);

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
        if (gameState != PossibleGameState.GAME_ROOM && receivedConnectionMessage.getContent() == MessageContent.ADD_PLAYER) {
            // if the player wants to disconnect from the game
            if (((LobbyMessage) receivedConnectionMessage).isDisconnection()) {
            } else {
                return reconnectionHandler((LobbyMessage) receivedConnectionMessage);
            }
        }
        return buildInvalidResponse();
    }

    /**
     * Method used to handle the reconnection of a player from the game
     *
     * @param receivedConnectionMessage message received by the server from the player asking to reconnect to the game
     * @return a {@link Message Message} which contains the result of the received message
     */
    protected Message reconnectionHandler(LobbyMessage receivedConnectionMessage) {
        String reconnectingPlayerName = receivedConnectionMessage.getSenderUsername();
        List<String> playersNames = game.getPlayers().stream().map(Player::getName).collect(Collectors.toList());

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
        } else {
            return new Response("Reconnection message from already in lobby Player", MessageStatus.ERROR);
        }
        server.sendMessage(reconnectingPlayerName, new GameStateResponse(reconnectingPlayerName, game.getCurrentPlayer().getName(), server.getFilepath()));
        return new ReconnectionRequest("Reconnection request", receivedConnectionMessage.getToken());
    }

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
                // set the last connected player as the winner
                for (Player p : game.getPlayers()) {
                    if (p.isConnected()) {
                        p.setWinner(true);
                    }
                }
                sendEndGame();
            }
        };
        reconnectionTimer.schedule(task, JsonReader.getTimer());
    }
}

