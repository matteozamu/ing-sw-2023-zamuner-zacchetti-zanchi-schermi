package it.polimi.ingsw.model;

public abstract class CommonGoal {
    private int currentPoints;

    public abstract int updateCurrentPoints(int points);
    public abstract boolean checkGoal();
}