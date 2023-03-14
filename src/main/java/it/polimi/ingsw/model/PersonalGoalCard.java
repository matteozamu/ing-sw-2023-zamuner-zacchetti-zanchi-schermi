package it.polimi.ingsw.model;

public class PersonalGoalCard {
    private PersonalGoal goals[];
    private int numTargetsReached;

    public PersonalGoalCard(PersonalGoal[] goals, int numTargetsReached) {
        this.goals = goals;
        this.numTargetsReached = numTargetsReached;
    }

    public int calculatePoints(Player p) {
        return 0;
    }

}