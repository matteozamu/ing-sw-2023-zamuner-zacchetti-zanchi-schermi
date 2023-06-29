package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board, consisting of a grid of ObjectCards with associated coordinates.
 * Provides methods for creating cells, checking if cells are empty, and removing ObjectCards from the board.
 */

public class Board implements Serializable {

    private Map<Coordinate, ObjectCard> grid;

    public Board() {
        this.grid = new HashMap<>();
    }

    public Board(Board board) {
        this.grid = board.getGrid();
    }


    /**
     * gets the grid of the board
     * @return the grid to get
     */
    public Map<Coordinate, ObjectCard> getGrid() {
        return grid;
    }

    /**
     * sets the grid of the board
     * @param grid the grid to set
     */
    public void setGrid(Map<Coordinate, ObjectCard> grid) {
        this.grid = grid;
    }

    /**
     * Remove the ObjectCard from the board at the specified coordinate.
     *
     * @param coordinate The coordinate of the ObjectCard to remove.
     * @return The ObjectCard that was removed, or null if there was no ObjectCard at the given coordinate.
     */
    public ObjectCard removeObjectCard(Coordinate coordinate) {
        return grid.remove(coordinate);
    }

    /**
     * Create a new cell of the board with the given coordinate and object card.
     *
     * @param coord The coordinate of the cell to create.
     * @param card  The object card to place at the cell's coordinate.
     * @return True if the cell is successfully created and added to the board, false otherwise.
     * @throws NullPointerException     If the coordinate is null.
     * @throws IllegalArgumentException If the object card is null.
     */
    public boolean createCell(Coordinate coord, ObjectCard card) throws NullPointerException, IllegalArgumentException {
        if (coord == null) throw new NullPointerException("Empty key");
        if (card == null) throw new IllegalArgumentException("Object card invalid");
        if (this.grid.containsKey(coord)) return false;

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
        Coordinate tmp = switch (direction) {
            case UP -> new Coordinate(coord.getRow() + 1, coord.getColumn());
            case DOWN -> new Coordinate(coord.getRow() - 1, coord.getColumn());
            case LEFT -> new Coordinate(coord.getRow(), coord.getColumn() - 1);
            case RIGHT -> new Coordinate(coord.getRow(), coord.getColumn() + 1);
        };

        return !grid.containsKey(tmp);
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
}