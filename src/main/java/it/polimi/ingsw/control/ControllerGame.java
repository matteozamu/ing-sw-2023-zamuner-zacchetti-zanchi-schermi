package it.polimi.ingsw.control;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.polimi.ingsw.model.Board.Direction.*;

/**
 * Controller for the game, handling game logic and interactions between model components.
 */
public class ControllerGame {
    private UUID id;
    private List<Player> players;
    private Board board;
    private List<CommonGoal> commonGoals;
    private Player currentPlayer;
//    private int numberOfPlayers;
    private Game game;
    private List<ObjectCard> limbo;

    /**
     * Constructor for the ControllerGame class, initializing the game state.
     */
    public ControllerGame() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.game = new Game();
//        this.numberOfPlayers = 0;
        this.commonGoals = new ArrayList<>();
        this.limbo = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Game getGame() {
        return game;
    }

    public List<ObjectCard> getLimbo() {
        return limbo;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Check if the username is available
     * @param username is the username of the player
     * @return true if available, false if not
     * @throws NullPointerException if username is null
     */
    public boolean isUsernameAvailable(String username) throws NullPointerException {
        if (username == null) throw new NullPointerException("Username is null");
        for (Player p : players){
            if (p.getName().equals(username)) return false;
        }
        return true;
    }

    /**
     * Add a new player to the game
     * @param username is the username of the player
     * @return true if successful, false otherwise
     */
    public boolean addPlayer(String username) {
        if(!this.isUsernameAvailable(username)) return false;

        if (this.players.size() < this.game.MAX_PLAYER) {
            Shelf shelf = new Shelf();
            PersonalGoalCard pg = game.getRandomAvailablePersonalGoalCard();

            Player p = new Player(username, shelf, pg);
            if(this.players.size() == 0) this.currentPlayer = p;
            this.players.add(p);
//            this.numberOfPlayers++;
            return true;
        } else return false;
    }

    /**
     * Move to the next player
     * @return the next player
     */
    public Player nextPlayer() {
        if(this.players.size() == 0) return null;

        this.limbo.clear();
        if (this.players.indexOf(this.currentPlayer) == this.players.size() - 1) this.currentPlayer = this.players.get(0);
        else this.currentPlayer = players.get(players.indexOf(currentPlayer) + 1);

        return this.currentPlayer;
    }

    /**
     * Fills the game board with object cards based on the number of players.
     * This method should be called at the beginning of the game to set up the board.
     */
    // TODO parametrizzare sul numero di giocatori
    public void fillBoard(){
        Coordinate c;
        try {
            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(5 - row, -5 + col);
                    this.board.createCell(c, game.getRandomAvailableObjectCard());
                }
            }
            for (int row = 5 - 1; row >= 1; row--) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(-5 + row, -5 + col);
                    this.board.createCell(c, game.getRandomAvailableObjectCard());
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
     * @param column is where the user want to add che ObjectCard
     */
    public void selectColumn(int column) {
        System.out.println("Seleziona una colonna: [0, 1, 2, 3, 4]");
        while (currentPlayer.getShelf().getAvailableRows(column) < limbo.size()){
            System.out.println("La colonna selezionata non ha abbastanza spazi");
            System.out.println("Seleziona una colonna: [0, 1, 2, 3, 4]");
        }
    }

    /**
     * Method that adds a list of ObjectCards in the first available cells of the specified column.
     *
     * @param col is the column where to insert the object cards.
     * @return true if the cards are successfully added.
     * @throws IllegalStateException if there is not enough space to add the cards.
     */
    public boolean addObjectCards(int col) {
        if (this.limbo.size() == 0 || this.limbo.size() > 3) return false;

        Shelf s = this.currentPlayer.getShelf();
        int availableRows = s.getAvailableRows(col);
        if (availableRows < this.limbo.size()) return false;

        for (ObjectCard card : this.limbo) {
            s.getGrid().put(new Coordinate(6 - availableRows, col), card);
            availableRows--;
        }
        if (s.getGrid().size() == s.ROWS * s.COLUMNS) s.setFull(true);

        return true;
    }

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
     * pick the ObjectCard from the board (if available)
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return the ObjectCard with that Coordinate
     */
//    public ObjectCard pickObjectCard(Coordinate coordinate) {
//        if(isObjectCardAvailable(coordinate)) {
//
//            return board.removeObjectCard(coordinate);
//        }
//        else return null;
//    }

    /**
     * Checks if the object card at the given coordinate is available (i.e., has at least one free side).
     * This method is used to determine if a player can pick up an object card from the board.
     *
     * @param coordinate The coordinate of the object card to check.
     * @return True if the object card is available, false otherwise.
     */
    public boolean isObjectCardAvailable(Coordinate coordinate) {
        System.out.println(board.isEmptyAtDirection(coordinate, UP));
        System.out.println(board.isEmptyAtDirection(coordinate, DOWN));
        System.out.println(board.isEmptyAtDirection(coordinate, RIGHT));
        System.out.println(board.isEmptyAtDirection(coordinate, LEFT));
        return board.isEmptyAtDirection(coordinate, UP) || board.isEmptyAtDirection(coordinate, DOWN) || board.isEmptyAtDirection(coordinate, RIGHT) || board.isEmptyAtDirection(coordinate, LEFT);
    }

    /**
     * Adds the object card at the specified coordinate to the limbo area.
     * The limbo area is used to store object cards that a player has picked up but not yet placed on their shelf.
     *
     * @param coordinate The coordinate of the object card to add to the limbo area.
     * @throws NullPointerException If the object card is null (should not happen).
     * @throws IllegalStateException If the object card is not available.
     * @throws IllegalArgumentException If the limbo area is already full.
     */
    public boolean addObjectCardToLimbo(Coordinate coordinate) throws NullPointerException {
        if(coordinate == null) throw new NullPointerException("ObjectCard is null");

        if(!isObjectCardAvailable(coordinate) || this.limbo.size() == 3) return false;

        this.limbo.add(this.board.removeObjectCard(coordinate));

        return true;
    }

    /**
     * Calculate the points of the currentPlayer. Each time the method counts the points starting from 0.
     * Then set the points to the currentPlayer.
     * @return the point of the currentPlayer
     */
    public int pointsCalculator() {
        int points = 0;
        points += this.currentPlayer.getPersonalGoalCard().calculatePoints();
        for(CommonGoal c: this.commonGoals){
            if(c.checkGoal(this.currentPlayer.getShelf())) points += c.updateCurrentPoints(this.players.size());
        }
        points += this.currentPlayer.getShelf().closeObjectCardsPoints();
        if(this.currentPlayer.getShelf().isFull()) points++;

        this.currentPlayer.setCurrentPoints(points);

        return points;
    }
}

