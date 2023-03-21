package it.polimi.ingsw.model;

public class Player {
    private String name;
    private int currentPoints;
    private Shelf shelf;
    private PersonalGoalCard personalGoalCard;

    public Player(String name, Shelf shelf, PersonalGoalCard personalGoalCard) {
        this.name = name;
        this.currentPoints = 0;
        this.shelf = shelf;
        this.personalGoalCard = personalGoalCard;
    }

    public int addPoints(int points) {
        return 0;
    }

    public String getName(){
        return this.name;
    }

    public Shelf getShelf() {
        return shelf;
    }

    @Override
    public String toString() {
        return name + ", points=" + currentPoints + ", personalGoalCard=" + personalGoalCard;
    }
}