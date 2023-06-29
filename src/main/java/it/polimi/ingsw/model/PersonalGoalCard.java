package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a personal goal card in the game, containing a list of personal goals and tracking the number of goals reached.
 */
public class PersonalGoalCard implements Serializable {
    private final ArrayList<PersonalGoal> goals;
    private final String ID;
    private int targetsReached;

    /**
     * Constructs a PersonalGoalCard with the given list of personal goals.
     *
     * @param goals An ArrayList of PersonalGoal objects representing the personal goals on the card.
     */
    public PersonalGoalCard(ArrayList<PersonalGoal> goals, String ID) {
        this.targetsReached = 0;
        this.goals = goals;
        this.ID = ID;
    }

    /**
     * return the number of targets reached
     *
     * @return a list of the goals of the Personal Goal Card
     */
    public ArrayList<PersonalGoal> getGoals() {
        return goals;
    }

    /**
     * return the id
     *
     * @return the ID of the Personal Goal Card
     */
    public String getID() {
        return ID;
    }

    /**
     * set the number of targets reached
     *
     * @param targetsReached is true if the target is reached
     */
    public void setTargetsReached(int targetsReached) {
        this.targetsReached = targetsReached;
    }

    /**
     * return the points of the target reached
     *
     * @param shelf is the shelf of the player
     * @return the points of the target reached
     */
    public int calculatePoints(Shelf shelf) {
        int targetReached = 0;
        int points = 0;
        for (PersonalGoal goal : this.goals) {
            if (shelf.getGrid().containsKey(new Coordinate(goal.getRow(), goal.getColumn()))) {
                if (shelf.getGrid().get(new Coordinate(goal.getRow(), goal.getColumn())) != null && shelf.getGrid().get(new Coordinate(goal.getRow(), goal.getColumn())).getType() == goal.getType()) {
                    targetReached++;
                }
            }
        }
        switch (targetReached) {
            case 1 -> points = 1;
            case 2 -> points = 2;
            case 3 -> points = 4;
            case 4 -> points = 6;
            case 5 -> points = 9;
            case 6 -> points = 12;
        }
        return points;
    }
}
