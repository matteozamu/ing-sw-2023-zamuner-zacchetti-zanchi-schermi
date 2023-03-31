package it.polimi.ingsw.model;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board, consisting of a grid of ObjectCards with associated coordinates.
 * Provides methods for creating cells, checking if cells are empty, and removing ObjectCards from the board.
 */

public class Board {
    private Map<Coordinate, ObjectCard> grid;

    public Board() {
        this.grid = new HashMap<>();
    }

    /**
     * Represents the four possible directions: UP, DOWN, LEFT, and RIGHT.
     */
    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Map<Coordinate, ObjectCard> getGrid() {
        return grid;
    }

    // TODO non rimuovere la cella ma eliminare solo il suo valore
    /**
     * Remove the ObjectCard from the board at the specified coordinate.
     *
     * @param coordinate The coordinate of the ObjectCard to remove.
     * @return The ObjectCard that was removed, or null if there was no ObjectCard at the given coordinate.
     */
    public ObjectCard removeObjectCard(Coordinate coordinate) {
        return grid.remove(coordinate);
    }

    public void clearGrid(){
        this.grid.clear();
    }

    /**
     * Create a new cell of the board with the given coordinate and object card.
     *
     * @param coord     The coordinate of the cell to create.
     * @param card  The object card to place at the cell's coordinate.
     * @return      True if the cell is successfully created and added to the board, false otherwise.
     * @throws IllegalArgumentException      If the coordinate is null.
     * @throws KeyAlreadyExistsException     If a cell with the same coordinate already exists.
     * @throws InvalidParameterException     If the object card is null.
     */
    public boolean createCell(Coordinate coord, ObjectCard card) throws IllegalArgumentException, KeyAlreadyExistsException, InvalidParameterException {
        if(coord == null) throw new IllegalArgumentException("Empty key");
        if (this.grid.containsKey(coord)) throw new KeyAlreadyExistsException("Cell " + coord.getColumn() + "," + coord.getRow() + " already exists");
        if(card == null) throw new InvalidParameterException("Object card invalid");

        this.grid.put(coord, card);
        return true;
    }

    /**
     * Checks if there is no ObjectCard in the specified direction from the given coordinate.
     *
     * @param coord     The coordinate of the ObjectCard to check.
     * @param direction The direction in which to check for an empty cell (UP, DOWN, LEFT, or RIGHT).
     * @return True if there is no ObjectCard in the specified direction from the given coordinate, false otherwise.
     * @throws IllegalArgumentException If the direction parameter is invalid.
     */
    public boolean isEmptyAtDirection(Coordinate coord, Direction direction) {
        Coordinate tmp;

        switch (direction) {
            case UP:
                tmp = new Coordinate(coord.getColumn() + 1, coord.getRow());
                break;
            case DOWN:
                tmp = new Coordinate(coord.getColumn() - 1, coord.getRow());
                break;
            case LEFT:
                tmp = new Coordinate(coord.getColumn(), coord.getRow() - 1);
                break;
            case RIGHT:
                tmp = new Coordinate(coord.getColumn(), coord.getRow() + 1);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }

        return !grid.containsKey(tmp);
    }

    @Override
    public String toString() {
        ObjectCard objectCard;
        String s = "";
        // piu efficiente con StringBuilder?

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