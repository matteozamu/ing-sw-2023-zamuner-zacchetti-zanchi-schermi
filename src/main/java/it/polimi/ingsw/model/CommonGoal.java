package it.polimi.ingsw.model;

// TODO : Le classi figlie di CommonGoal potrebbero essere finali?
// TODO : Modificare il numero di colonne e righe con shelf.COLUMNS e shelf.ROWS nelle classi figlie
// TODO : Modificare le classi figlie con Map<Coordinate, ObjectCard> grid = shelf.getGrid();
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