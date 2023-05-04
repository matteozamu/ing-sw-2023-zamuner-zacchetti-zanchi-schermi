package it.polimi.ingsw.control;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleGameState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.InputValidator;
import it.polimi.ingsw.utility.TimerRunListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.Board.Direction.*;

/**
 * Controller for the game, handling game logic and interactions between model components.
 */
public class ControllerGame implements TimerRunListener, Serializable {
    private final transient Server server;
    private UUID id;
    private Game game;
    private List<ObjectCard> limbo;
    private PossibleGameState gameState = PossibleGameState.GAME_ROOM;
    private boolean isLobbyFull;
    private transient TurnController turnController;


    /**
     * Constructor for the ControllerGame class, initializing the game state.
     */
    public ControllerGame(Server server) {
        this.server = server;
        this.id = UUID.randomUUID();
        this.game = Game.getInstance();
        this.limbo = new ArrayList<>();
        fillBoard();
    }

    public Message onMessage(Message receivedMessage) {
        Server.LOGGER.log(Level.SEVERE, "ONMESSAGE: {0}", receivedMessage);

        if (gameState == PossibleGameState.GAME_ROOM) {
            return firstStateHandler(receivedMessage);
        }

        if (gameState == PossibleGameState.GAME_ENDED) {
            return new Response("GAME ENDED", MessageStatus.ERROR);
        }

        if (!InputValidator.validateInput(receivedMessage) || (gameState != PossibleGameState.GAME_ROOM && !this.getGame().doesPlayerExists(receivedMessage.getSenderUsername()))) {
            return buildInvalidResponse();
        }

        return new Response("GAME STATE ERROR FOR THIS MESSAGE", MessageStatus.ERROR);
    }

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

    private Response numberOfPlayersMessageHandler(NumberOfPlayersMessage numberOfPlayersMessage) {
        int numberOfPlayers = numberOfPlayersMessage.getNumberOfPlayers();

        if (numberOfPlayersMessage.getContent() == MessageContent.NUMBER_OF_PLAYERS && numberOfPlayers >= Game.MIN_PLAYERS && numberOfPlayers <= game.MAX_PLAYERS) {
            this.game.setNumberOfPlayers(numberOfPlayersMessage.getNumberOfPlayers());

            Server.LOGGER.log(Level.INFO, "Number of players for the game: {0}", numberOfPlayersMessage.getNumberOfPlayers());
        } else {
            return buildInvalidResponse();
        }

        return new Response("Number of players set", MessageStatus.OK);
    }

    private Response lobbyMessageHandler(LobbyMessage lobbyMessage) {
        List<Player> inLobbyPlayers = game.getPlayers();

        if (lobbyMessage.getContent() == MessageContent.ADD_PLAYER && isUsernameAvailable(lobbyMessage.getSenderUsername()) && !lobbyMessage.isDisconnection()) {
            if (inLobbyPlayers.size() < this.game.MAX_PLAYERS) {
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

    public PossibleGameState getGameState() {
        return gameState;
    }

    public Game getGame() {
        return game;
    }

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

    private void startingStateHandler() {
        this.turnController = new TurnController(this.game.getPlayers());
        gameState = PossibleGameState.GAME_STARTED;

//        UserPlayer firstPlayer = roundController.getTurnManager().getTurnOwner();

        // I first need to pick the two powerups for the first player playing
//        roundController.pickTwoPowerups();

        server.sendMessageToAll(new GameStartMessage(game.getCurrentPlayer().getName()));
        sendPrivateUpdates();
    }

    /**
     * This method sends to all clients the new state of the {@link Game Game}, contained in the
     * {@link model.GameSerialized GameSerialized}. This method is used to send an update of the
     * {@link Game Game} everytime that a normal action is completed
     */
    public void sendPrivateUpdates() {
        List<Player> players = game.getPlayers();

        // TODO si potrebbe usare sendMessageToAll?
        for (Player player : players) {
            server.sendMessage(player.getName(), new GameStateMessage(player.getName(), game.getCurrentPlayer().getName()));
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
    // TODO: parametrizzare sul numero di giocatori
    // TODO: non sarebbe meglio spostarlo nel model?
    public void fillBoard() {
        Coordinate c;
        Board b = game.getBoard();
        try {
            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(5 - row, -5 + col);
                    b.createCell(c, game.getRandomAvailableObjectCard());
                }
            }
            for (int row = 5 - 1; row >= 1; row--) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(-5 + row, -5 + col);
                    b.createCell(c, game.getRandomAvailableObjectCard());
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

    @Override
    public void onTimerRun() {
        // TODO
    }
}

