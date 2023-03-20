package it.polimi.ingsw.model;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.InvalidParameterException;
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

    public boolean createCell(Coordinate c, ObjectCard card) throws NullPointerException, KeyAlreadyExistsException, InvalidParameterException {
        //TODO: sistemare
        if (this.grid.containsKey(c)) throw new KeyAlreadyExistsException("Cell " + c.getX() + "," + c.getY() + " already exists");
        if(c == null) throw new NullPointerException("Empty key");
        if(card == null){
            throw new InvalidParameterException("Object card invalid");
        }
        this.grid.put(c, card);
        return true;
    }

    public void clearGrid(){
        this.grid.clear();
    }

}