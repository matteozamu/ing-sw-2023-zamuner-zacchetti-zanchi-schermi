package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO CREAZIONE MAPPA DELLA SHELF SE NO NON POSSIAMO ITERARE OPPURE
//  CAMBiArE IL MODO DI CONTROLLARE IL NUMERO DI RIGHE DISPONIBILI

/**
 * Represents a Shelf in the game, which holds ObjectCards.
 * The origin of the coordinates in the grid is in the lower left corner.
 */
public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private boolean isFull;
    public int numberOfCards;
    public final int ROWS;
    public final int COLUMNS;

    /**
     * Constructs a new Shelf.
     */
    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
        this.numberOfCards = 0;
        this.ROWS = 6;
        this.COLUMNS = 5;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    /**
     *
     * @return true if the shelf is full
     */
    public boolean getFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    /**
     * return the number (row) of free cells in the col column
     * if there are no free cells the method throws an exception
     * @param col is the column
     * @return number of free cell for the col column
     */
    public int getAvailableRows(int col) {
        int availableRows = 6;
        Coordinate coordinate;

        for (int i = 0; i < this.ROWS; i++) {
            coordinate = new Coordinate(i, col);
            if (this.grid.containsKey(coordinate) && this.grid.get(coordinate) != null) availableRows--;
        }
        return availableRows;
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
     * method that returns the map of the grid
     * @return The grid representing the Shelf
     */
    public Map<Coordinate, ObjectCard> getGrid() {
        return this.grid;
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

    @Override
    public String toString() {
        return "Shelf{" +
                "grid=" + grid +
                '}';
    }
}