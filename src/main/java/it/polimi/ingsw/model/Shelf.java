package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Shelf in the game, which holds ObjectCards.
 * The origin of the coordinates in the grid is in the lower left corner.
 */
public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private boolean isFull;
    private int numberOfCards;
    public final int ROWS = 6;
    public final int COLUMNS = 5;

    /**
     * Constructs a new Shelf.
     */
    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
        this.numberOfCards = 0;
    }

    /**
     * method that return the number (row) of free cells in the col column
     * if there are no free cells the method throws an exception
     * @param col is the column
     * @return number of free cell for the col column
     */
    public int getAvailableRows(int col) throws NullPointerException {
        for (int row = ROWS - 1; row >= 0; row--) {
            Coordinate coordinate = new Coordinate(col, row);
            if (grid.get(coordinate) == null) {
                return row;
            }
        }
        throw new NullPointerException("Chosen column is full");
    }

    /**
     * method that add an ObjectCard in the first free cell of the col column
     * @param col is the column where to enter the object card
     * @param card is the object card to be inserted in the column col
     */
    public boolean addObjectCard(int col, ObjectCard card) {
        //TODO: OK, da spostare nel controller
        int row = getAvailableRows(col);
        if (row != -1) {
            grid.put(new Coordinate(col, row), card);
            numberOfCards++;
            if (numberOfCards == ROWS * COLUMNS) {
                isFull = true;
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @return true if the shelf is full
     */
    public boolean checkFull() {
        return isFull;
    }

    /**
     * method that returns the map of the grid
     * @return The grid representing the Shelf
     */
    public Map<Coordinate, ObjectCard> getGrid() {
        return Collections.unmodifiableMap(grid);
    }

    /**
     * Returns the ObjectCard at the specified coordinate in the shelf.
     *
     * @param coord The Coordinate object representing the position in the shelf.
     * @return The ObjectCard at the given position, or null if the position is empty.
     */
    public ObjectCard getObjectCard(Coordinate coord) {
        return grid.get(coord);
    }


}