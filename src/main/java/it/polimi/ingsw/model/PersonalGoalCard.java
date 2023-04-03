package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Represents a personal goal card in the game, containing a list of personal goals and tracking the number of goals reached.
 */
public class PersonalGoalCard {
    private ArrayList<PersonalGoal> goals;
    private int targetsReached;

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
     * Calculates the points earned by a player based on their personal goal card.
     *
     * @return The number of points earned by the player.
     */
    public int calculatePoints() {
        if(this.targetsReached == 1) return 1;
        if(this.targetsReached == 2) return 2;
        if(this.targetsReached == 3) return 4;
        if(this.targetsReached == 4) return 6;
        if(this.targetsReached == 5) return 9;
        if(this.targetsReached == 6) return 12;

        return 0;
    }

    public ArrayList<PersonalGoal> getGoals() {
        return goals;
    }
}
