package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Represents a Shelf in the game, which holds ObjectCards.
 * The origin of the coordinates in the grid is in the lower left corner.
 */
public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private boolean isFull;
    public int numberOfCards;
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
                return row + 1;
            }
        }
        throw new NullPointerException("Chosen column is full");
    }

    /**
     * Returns a map with the number of free cells for each column in the Shelf.
     *
     * @return A Map<Integer, Integer> where the key represents the column index and the value
     * represents the number of free cells in that column.
     */
    public Map<Integer, Integer> getFreeCellsPerColumn() {
        Map<Integer, Integer> freeCellsPerColumn = new HashMap<>();

        for (int col = 0; col < COLUMNS; col++) {
            try {
                int freeRows = getAvailableRows(col);
                freeCellsPerColumn.put(col, freeRows);
            } catch (NullPointerException e) {
                freeCellsPerColumn.put(col, 0);
            }
        }

        return freeCellsPerColumn;
    }

    /**
     * Method that adds a list of ObjectCards in the first available cells of the specified column.
     *
     * @param col   is the column where to insert the object cards.
     * @param cards is the list of object cards to be inserted in the column col.
     * @return true if the cards are successfully added.
     * @throws IllegalStateException if there is not enough space to add the cards.
     */
    public boolean addObjectCards(int col, List<ObjectCard> cards) throws IllegalStateException {
        //TODO: OK, da spostare nel controller
        if (cards.size() < 1 || cards.size() > 3) {
            throw new IllegalArgumentException("The list of cards must have a size between 1 and 3.");
        }

        int row;
        try {
            row = getAvailableRows(col);
        } catch (NullPointerException e) {
            throw new IllegalStateException("Not enough space in the column: " + col);
        }

        if (row - cards.size() + 1 < 0) {
            throw new IllegalStateException("Not enough space in the column: " + col);
        }

        for (ObjectCard card : cards) {
            grid.put(new Coordinate(col, row), card);
            numberOfCards++;
            row--;
        }

        if (numberOfCards == ROWS * COLUMNS) {
            isFull = true;
        }

        return true;
    }

    /**
     *
     * @return true if the shelf is full
     */
    public boolean getFull() {
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