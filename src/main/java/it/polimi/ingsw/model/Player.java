package it.polimi.ingsw.model;

/**
 * Represents a player in the game, including their name, current points, shelf, and personal goal card.
 */
public class Player {
    private final String name;
    private int currentPoints;
    private Shelf shelf;
    private final PersonalGoalCard personalGoalCard;

    /**
     * Constructs a Player with the given name, shelf, and personal goal card.
     *
     * @param name The name of the player.
     * @param shelf The Shelf object representing the player's shelf.
     * @param personalGoalCard The PersonalGoalCard object representing the player's personal goal card.
     */
    public Player(String name, Shelf shelf, PersonalGoalCard personalGoalCard) {
        this.name = name;
        this.currentPoints = 0;
        this.shelf = shelf;
        this.personalGoalCard = personalGoalCard;
    }

    /**
     * Adds points to the player's current points.
     *
     * @param points The number of points to add.
     * @return The updated number of points.
     */
    public int addPoints(int points) {
        return 0;
    }

    /**
     * Returns the player's name.
     *
     * @return The name of the player.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the player's shelf.
     *
     * @return The Shelf object representing the player's shelf.
     */
    public Shelf getShelf() {
        return shelf;
    }

    /**
     * Returns the player's current points.
     *
     * @return The number of points the player currently has.
     */
    public int getCurrentPoints() {
        return currentPoints;
    }

    /**
     * Returns the player's personal goal card.
     *
     * @return The PersonalGoalCard object representing the player's personal goal card.
     */
    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    /**
     * Returns a string representation of the player, including their name, current points, and personal goal card.
     *
     * @return A string representation of the player.
     */
    @Override
    public String toString() {
        return name + ", points=" + currentPoints + ", personalGoalCard=" + personalGoalCard;
    }

}
