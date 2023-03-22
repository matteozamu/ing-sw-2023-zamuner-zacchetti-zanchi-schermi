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
     * @param x is the column
     * @return number of free cell for the x column
     */
    public int getAvailableRows(int x) {
        // TODO : shelf.ROWS
        for (int y = 5; y >= 0; y--) {
            Coordinate coordinate = new Coordinate(x, y);
            if (grid.get(coordinate) == null) {
                return y; //TODO : forse è y-1
            }
        }
        return -1;
    }

    /**
     * method that add an ObjectCard in the first free cell of the x column
     * @param column
     * @param card
     */
    public boolean addObjectCard(int column, ObjectCard card) {
        //TODO: OK ma da spostare nel controller
        int y = getAvailableRows(column);
        if (y != -1) {
            grid.put(new Coordinate(column, y), card);
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