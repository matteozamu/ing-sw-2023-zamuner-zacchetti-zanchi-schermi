package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a personal goal card in the game, containing a list of personal goals and tracking the number of goals reached.
 */
public class PersonalGoalCard implements Serializable {
    private ArrayList<PersonalGoal> goals;
    private int targetsReached;
    private int ID;

    /**
     * Constructs a PersonalGoalCard with the given list of personal goals.
     *
     * @param goals An ArrayList of PersonalGoal objects representing the personal goals on the card.
     */
    public PersonalGoalCard(ArrayList<PersonalGoal> goals) {
        this.targetsReached = 0;
        this.goals = goals;
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
    public int getID() {
        return ID;
    }

    /**
     * @param targetsReached is true if the target is reached
     */
    public void setTargetsReached(int targetsReached) {
        this.targetsReached = targetsReached;
    }

    /**
     * Calculates the points earned by a player based on their personal goal card.
     *
     * @return The number of points earned by the player.
     */
    public int calculatePoints() {
        if (this.targetsReached == 1) return 1;
        if (this.targetsReached == 2) return 2;
        if (this.targetsReached == 3) return 4;
        if (this.targetsReached == 4) return 6;
        if (this.targetsReached == 5) return 9;
        if (this.targetsReached == 6) return 12;

        return 0;
    }
}
