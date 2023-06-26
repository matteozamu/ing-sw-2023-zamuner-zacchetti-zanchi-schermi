package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a personal goal card in the game, containing a list of personal goals and tracking the number of goals reached.
 */
public class PersonalGoalCard implements Serializable {
    private final ArrayList<PersonalGoal> goals;
    private int targetsReached;
    private final String ID;

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
     * @return a list of the goals of the Personal Goal Card
     */
    public ArrayList<PersonalGoal> getGoals() {
        return goals;
    }

    /**
     * @return the ID of the Personal Goal Card
     */
    public String getID() {
        return ID;
    }

    /**
     * @param targetsReached is true if the target is reached
     */
    public void setTargetsReached(int targetsReached) {
        this.targetsReached = targetsReached;
    }

    /**
     * return the points to add to the player's score
     * @param shelf is the shelf of the player
     * @return the points to add to the player's score
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
        // return the difference between the current points and the previous points, in order to add it to the current points
        switch (targetReached) {
            case 1, 2 -> points = 1;
            case 3, 4 -> points = 2;
            case 5, 6 -> points = 3;
        }
        return points;
    }
}
