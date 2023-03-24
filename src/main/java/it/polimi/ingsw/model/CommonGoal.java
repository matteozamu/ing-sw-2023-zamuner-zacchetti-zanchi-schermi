package it.polimi.ingsw.model;

/**
 * Represents a common goal in the game.
 * To initialize the children classes, use: CommonGoal goal = new CommonGoalType1();
 * Note: Child classes don't need a constructor.
 */
public abstract class CommonGoal {
    private int currentPoints = 8;

    /**
     * Update the points obtainable with the current CommonGoalCard.
     *
     * @param points The number of points to update.
     * @return The updated number of points for the current CommonGoalCard.
     */
    public int updateCurrentPoints(int points) {
        return 0;
    }

    /**
     * Check if the given shelf meets the requirements of the common goal.
     *
     * @param shelf The Shelf object to evaluate.
     * @return True if the shelf meets the requirements, false otherwise.
     */
    public abstract boolean checkGoal(Shelf shelf);
}
