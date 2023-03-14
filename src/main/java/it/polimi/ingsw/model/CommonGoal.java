package it.polimi.ingsw.model;

public abstract class CommonGoal {
    private int currentPoints;

    public int updateCurrentPoints(int points){
        return 0;
    }
    public abstract boolean checkGoal();
}