package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

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
     * @param p The Player for whom the points are calculated.
     * @return The number of points earned by the player.
     */
    public int calculatePoints(Player p) {
        return 0;
    }

}
