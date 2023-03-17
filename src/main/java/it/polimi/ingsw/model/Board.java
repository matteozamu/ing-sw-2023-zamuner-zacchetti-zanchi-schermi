package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Coordinate, ObjectCard> grid;
//    private ObjectCard grid[][];
//    private boolean gridAvailability[][];
    public int ROWS;
    public int COLUMNS;

    public Board() {
        this.grid = new HashMap<>();
    }

    public Map<Coordinate, ObjectCard> getGrid() {
        return grid;
    }

    // controllare se la cella libera
    public void addCard(Coordinate c, ObjectCard card) {
        this.grid.put(c, card);
    }

}