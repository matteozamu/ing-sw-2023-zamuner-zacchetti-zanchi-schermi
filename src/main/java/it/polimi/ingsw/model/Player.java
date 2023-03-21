package it.polimi.ingsw.model;

public class Player {
    private final String name;
    private int currentPoints;
    private Shelf shelf;
    private final PersonalGoalCard personalGoalCard;

    public Player(String name, Shelf shelf, PersonalGoalCard personalGoalCard) {
        this.name = name;
        this.currentPoints = 0;
        this.shelf = shelf;
        this.personalGoalCard = personalGoalCard;
    }

    /**
     * Method adding points
     * @param points
     * @return
     */
    public int addPoints(int points) {
        return 0;
    }

    /**
     * Method returning player's name
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Method returning player's Shelf
     * @return Shelf
     */
    public Shelf getShelf() {
        return shelf;
    }

    @Override
    public String toString() {
        return name + ", points=" + currentPoints + ", personalGoalCard=" + personalGoalCard;
    }
}