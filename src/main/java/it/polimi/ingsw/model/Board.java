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
        if (this.grid.containsKey(c)) throw new KeyAlreadyExistsException("Cell " + c.getX() + "," + c.getY() + " already exists");
        if(c == null) throw new NullPointerException("Empty key");
        if(card == null) throw new InvalidParameterException("Object card invalid");

        this.grid.put(c, card);
        return true;
    }

    public void clearGrid(){
        this.grid.clear();
    }

    @Override
    public String toString() {
        ObjectCard objectCard;
        String s = "";

        for (int row = 1; row <= 5; row++) {
            for (int espacios = 5 - row; espacios >0; espacios--) s += "\t\t";
            for (int col = 1; col < 2 * row; col++) {
                objectCard = this.grid.get(new Coordinate(5 - row, -5 + col));
                s += ("|" + objectCard);
            }
            s += "|\n";
        }
        for (int row = 5 - 1; row >= 1; row--) {
            for (int espacios = 5 - row; espacios >0; espacios--) s += "\t\t";
            for (int col = 1; col < 2 * row; col++) {
                objectCard = this.grid.get(new Coordinate(-5 + row, -5 + col));
                s += ("|"+ objectCard);
            }
            s += "|\n";
        }
        return s;
    }
}