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

    /**
     * Check if the given shelf meets the requirements of the common goal.
     *
     * @param shelf The Shelf object to evaluate.
     * @return True if the shelf meets the requirements, false otherwise.
     */
    public abstract boolean checkGoal(Shelf shelf);
}
