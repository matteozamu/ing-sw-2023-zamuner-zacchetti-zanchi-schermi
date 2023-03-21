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
     *
     * @param x is the column
     * @return number of free cell for the x column
     */
    public int getNextAvailableRow(int x) {
        for (int y = 5; y >= 0; y--) {
            Coordinate coordinate = new Coordinate(x, y);
            if (grid.get(coordinate) == null) {
                return y;
            }
        }
        return -1; // Nessuna casella libera disponibile nella colonna specificata
    }


    public void addObjectCard(int x, ObjectCard card) {
        //TODO: Da revisionare
        int y = getNextAvailableRow(x);
        if (y != -1) {
            grid.put(new Coordinate(x, y), card);
            numberOfCards++;
            if (numberOfCards == ROWS * COLUMNS) {
                isFull = true;
            }
        }
    }

    public boolean checkFull() {
        return isFull;
    }

    public Map<Coordinate, ObjectCard> getGrid() {
        return Collections.unmodifiableMap(grid);
    }

    public ObjectCard getObjectCard(Coordinate coordinate) {
        return grid.get(coordinate);
    }

}