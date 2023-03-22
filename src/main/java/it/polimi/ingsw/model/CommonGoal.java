package it.polimi.ingsw.model;


// Per inizializzare i figli: CommonGoal goal = new CommonGoalType1();
// Le classi figlie non hanno bisogno del costruttore

public abstract class CommonGoal {
    private int currentPoints = 8;

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