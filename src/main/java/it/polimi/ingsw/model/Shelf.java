package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private Boolean isFull;
    private int numberOfCards;
    private final int rows = 6;
    private final int columns = 5;

    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
        this.numberOfCards = 0;
    }

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
        // Capire come gestire la coordinata y
    }

    public boolean checkFull() {
        return false;
    }

    public Map<Coordinate, ObjectCard> getGrid() {
        return Collections.unmodifiableMap(grid);
    }

    public ObjectCard getObjectCard(Coordinate coordinate) {
        return grid.get(coordinate);
    }

}