package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private Boolean isFull;
    private int numbersOfCards;

    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
        this.numbersOfCards = 0;
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