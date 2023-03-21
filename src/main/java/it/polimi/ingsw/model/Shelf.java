package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private Boolean isFull;
    private int numberOfCards;
    private final int ROWS = 6;
    private final int COLUMNS = 5;

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
    public void addObjectCard(int column, ObjectCard card) {
        //TODO: Da revisionare (far sapere a chi chiama se la carta oggetto è stata correttamente aggiunta. Non aggiungere carte se non c'è spazio nella shelf)
        int y = getAvailableRows(column);
        if (y != -1) {
            grid.put(new Coordinate(column, y), card);
            numberOfCards++;
            if (numberOfCards == ROWS * COLUMNS) {
                isFull = true;
            }
        }
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