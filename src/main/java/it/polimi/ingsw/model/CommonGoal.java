package it.polimi.ingsw.model;

public class CommonGoal {
    private int id;
    private int currentPoints;

    public CommonGoal(int id, int currentPoints) {
        this.id = id;
        this.currentPoints = currentPoints;
    }

    public int updateCurrentPoints(int points) {
        return 0;
    }

}