package it.polimi.ingsw.model;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Coordinate, ObjectCard> grid;
    // TODO : ROWS e COLUMNS possono essere final?
    private int ROWS;
    private int COLUMNS;

    public Board() {
        // TODO : Le righe e le colonne non sono da inizializzare?
        this.grid = new HashMap<>();
    }

    public Map<Coordinate, ObjectCard> getGrid() {
        return grid;
    }

    public boolean createCell(Coordinate c, ObjectCard card) throws NullPointerException, KeyAlreadyExistsException, InvalidParameterException {
        // TODO : forse conviene usare IllegalArgumentException al posto di NullPointerException
        if(c == null) throw new NullPointerException("Empty key");
        if (this.grid.containsKey(c)) throw new KeyAlreadyExistsException("Cell " + c.getX() + "," + c.getY() + " already exists");
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
        //TODO : invece di usare un tipo String conviene usare un tipo StringBuilder
        // in quanto si sta creando una stringa mano a mano nei cicli e ciò comporta
        // la crazione ogni volta di nuove istanze della stringa. Quindi si potrebbero
        // sostituire gli operatori di concatenazione di stringhe (+=) con i metodi append()
        // dell'oggetto StringBuilder.

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

    /**
     * this function check if there is an ObjectCard over the coordinate given
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return false if in the map there is the coordinate over the one passed by parameter
     */
    public boolean isEmptyUp(Coordinate coordinate) {
        Coordinate tmp = new Coordinate(coordinate.getX() + 1, coordinate.getY());
        if (grid.containsKey(tmp)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * this function check if there is an ObjectCard down the coordinate given
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return false if in the map there is the coordinate under the one passed by parameter
     */
    public boolean isEmptyDown(Coordinate coordinate) {
        Coordinate tmp = new Coordinate(coordinate.getX() - 1, coordinate.getY());
        return !grid.containsKey(tmp);
    }

    /**
     * this function check if there is an ObjectCard on the right of the coordinate given
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return false if in the map there is the coordinate on the right of the one passed by parameter
     */
    public boolean isEmptyRight(Coordinate coordinate) {
        Coordinate tmp = new Coordinate(coordinate.getX(), coordinate.getY() + 1 );
        return !grid.containsKey(tmp);
    }

    /**
     * this function check if there is an ObjectCard on the left of the coordinate given
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return false if in the map there is the coordinate on the left of the one passed by parameter
     */
    public boolean isEmptyLeft(Coordinate coordinate) {
        Coordinate tmp = new Coordinate(coordinate.getX(), coordinate.getY() - 1 );
        return !grid.containsKey(tmp);
    }

    /**
     * remove the ObjectCard from the board
     * @param coordinate of the ObjectCard to remove
     * @return the ObjectCard removed
     */
    public ObjectCard removeObjectCard(Coordinate coordinate) {
        return grid.remove(coordinate);
    }
}