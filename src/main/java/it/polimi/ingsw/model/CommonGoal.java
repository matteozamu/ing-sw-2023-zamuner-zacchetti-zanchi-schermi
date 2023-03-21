package it.polimi.ingsw.model;

public abstract class CommonGoal {
    private int currentPoints;

    /**
     * Update the points obtainable with the current CommonGoalCard
     * @param points
     * @return
     */
    public int updateCurrentPoints(int points) {
        return 0;
    }

    /**
     * Abstract parent method of CommonGoalTypeX class methods.
     * @param shelf
     * @return
     */
    public abstract boolean checkGoal(Shelf shelf);
}