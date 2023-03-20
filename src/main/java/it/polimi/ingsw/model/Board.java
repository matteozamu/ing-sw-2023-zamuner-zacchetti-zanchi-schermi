package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Coordinate, ObjectCard> grid;
    public int ROWS;
    public int COLUMNS;

    public Board() {
        this.grid = new HashMap<>();
    }

    public Map<Coordinate, ObjectCard> getGrid() {
        return grid;
    }

    public void addCard(Coordinate c, ObjectCard card) throws NullPointerException, IllegalStateException {
        if(c == null) throw new NullPointerException("Empty key");
        if(this.grid.get(c) == null){
            this.grid.put(c, card);
        } else {
            throw new IllegalStateException("Cell " + c.getX() + "," + c.getY() + " is not empty");
        }
    }

    public void clearGrid(){
        this.grid.clear();
    }

}