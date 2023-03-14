package it.polimi.ingsw.model;

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
    }

    public boolean checkFull() {
        return false;
    }

}