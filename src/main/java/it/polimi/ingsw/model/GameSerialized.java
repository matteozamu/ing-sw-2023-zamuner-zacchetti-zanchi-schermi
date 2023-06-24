package it.polimi.ingsw.model;

import it.polimi.ingsw.utility.JsonReader;

import java.io.Serializable;
import java.util.*;

/**
 * this class represents the game state, it is used to send the game state to the clients
 */
public class GameSerialized implements Serializable {
    private static final long serialVersionUID = 526685006552543525L;
    private ArrayList<Player> players;
    private Board board;

    // attributes for each single player, initialized thanks to the username passed to the constructor
    private int points;
    private Shelf shelf;
    private PersonalGoalCard personalGoalCard;
    private LinkedHashMap<Coordinate, ObjectCard> limbo;
    private Player currentPlayer;
    private int[][] boardMatrix;


    /**
     * constructor of the class, it initializes the attributes of the class
     *
     * @param username is the username of the player that is asking for the game state
     */
    public GameSerialized(String username, String filepath) {
        Game instance = Game.getInstance(username);

        if (instance.getPlayers() != null) {
            this.players = new ArrayList<>(instance.getPlayers());
        } else {
            this.players = new ArrayList<>();
        }

        this.board = new Board(instance.getBoard());
        this.limbo = new LinkedHashMap<>(instance.getLimbo());
        this.currentPlayer = instance.getCurrentPlayer();

        Player player = instance.getPlayerByName(username);
        this.shelf = player.getShelf();
        this.personalGoalCard = player.getPersonalGoalCard();
        this.points = player.getCurrentPoints();
        JsonReader.readJsonConstant(filepath);
        this.boardMatrix = JsonReader.getBoard(players.size());
    }

    /**
     * this method returns the board matrix
     *
     * @return the board matrix
     */
    public int[][] getBoardMatrix() {
        return boardMatrix;
    }

    /**
     * this method returns the list of the players
     *
     * @return the list of the players
     */
    // TODO: Si può eliminare?
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * this method returns the points of the player
     *
     * @return the points of the player
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return the list of all the players in the game
     */
    public List<Player> getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>(players);

        return allPlayers;
    }

    /**
     * this method returns the board
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This method returns the shelf of the player
     *
     * @return the shelf of the player
     */
    public Shelf getShelf() {
        return shelf;
    }

    /**
     * This method returns the personal goal card of the player
     *
     * @return the personal goal card of the player
     */
    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    /**
     * This method returns the limbo
     *
     * @return the limbo
     */
    public LinkedHashMap<Coordinate, ObjectCard> getLimbo() {
        return limbo;
    }

    /**
     * This method returns the list of all the limbo cards
     *
     * @return the list of all the limbo cards
     */
    public List<ObjectCard> getAllLimboCards() {
        List<ObjectCard> allLimboCards = new ArrayList<>(limbo.values());

        return allLimboCards;
    }

    @Override
    public String toString() {
        return "GameSerialized{" +
                "players=" + players +
                ", board=" + board +
                ", points=" + points +
                ", shelf=" + shelf +
                ", personalGoalCard=" + personalGoalCard +
                ", limbo=" + limbo +
                ", currentPlayer=" + currentPlayer +
                '}';
    }
}
