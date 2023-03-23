package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private Boolean isFull;
    private int numberOfCards;
    public final int ROWS = 6;
    public final int COLUMNS = 5;

    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
        this.numberOfCards = 0;
    }

    /**
     * method that return che number (y) of free cells in the x column
     * if there are no free cells the methods return -1
     * @param col is the column
     * @return number of free cell for the x column
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
     * method that add an ObjectCard in the first free cell of the x column
     * @param column
     * @param card
     */
    public boolean addObjectCard(int column, ObjectCard card) {
        //TODO: OK, da spostare nel controller
        int row = getAvailableRows(column);
        if (row != -1) {
            grid.put(new Coordinate(column, row), card);
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
    public Boolean getFull() {
        return isFull;
    }
    /**
     * method that returns the map of the grid
     * @return
     */
    public Map<Coordinate, ObjectCard> getGrid() {
        return Collections.unmodifiableMap(grid);
    }

    /**
     * method that return che ObjectCard which is in the coordinate "coord" of the shelf
     * @param coord
     * @return the ObjectCard in that position
     */
    public ObjectCard getObjectCard(Coordinate coord) {
        return grid.get(coord);
    }

}