package it.polimi.ingsw.model;

/**
 * Represents a common goal in the game.
 * To initialize the children classes, use: CommonGoal goal = new CommonGoalType1();
 * Note: Child classes don't need a constructor.
 */
public abstract class CommonGoal {
    private int currentPoints;

    public CommonGoal() {
        this.currentPoints = 8;
    }

    public abstract int getType();

    public abstract String cliView();

    /**
     * Returns a string representation of the common goal, describing its requirements and conditions.
     *
     * @return A string representing the common goal.
     */
    public abstract String toString();

    /**
     * Check if the given shelf meets the requirements of the common goal.
     *
     * @param shelf The Shelf object to evaluate.
     * @return True if the shelf meets the requirements, false otherwise.
     */
    public abstract boolean checkGoal(Shelf shelf);

    /**
     * Determines if the given shelf is eligible to be checked for the specific common goal.
     * Each subclass of CommonGoal should provide its own implementation of this method,
     * based on the specific conditions that must be met for the goal to be achievable.
     *
     * @param shelf The shelf to be checked for eligibility.
     * @return true if the shelf is eligible for the specific common goal, false otherwise.
     */
    protected abstract boolean isShelfEligible(Shelf shelf);

    /**
     * Gets the current points for this common goal.
     *
     * @return The current points for this common goal.
     */
    public int getCurrentPoints() {
        return currentPoints;
    }

    /**
     * Update the points obtainable with the current CommonGoalCard.
     *
     * @param numberOfPlayers is the number of player in the game
     * @return The updated number of points for the current CommonGoalCard.
     */
    public int updateCurrentPoints(int numberOfPlayers) {
        if(numberOfPlayers == 4 || numberOfPlayers == 3){
            this.currentPoints -= 2;
        } else if (numberOfPlayers == 2){
            this.currentPoints -=4;
        }
        return currentPoints;
    }
}
