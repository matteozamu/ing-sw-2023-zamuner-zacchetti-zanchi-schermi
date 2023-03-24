package it.polimi.ingsw.model;

import java.util.Objects;

//TODO : siccome la Shelf e la Board hanno due sistemi di riferimento differenti
// conviene creare due classi figlie della classe Coordinate (ad esempio ShelfCoordinate e BoardCoordinate).
// Per poter gestire più facilmente i casi in cui ci si muove con le coordinate al di fuori dei limiti della Shelf o della Board.

/**
 * Represents a 2D coordinate in the game grid with methods to obtain neighboring coordinates.
 */
public class Coordinate {
    private int column;
    private int row;

    /**
     * Constructs a new Coordinate with the given column and row values.
     *
     * @param column The column value for the coordinate.
     * @param row    The row value for the coordinate.
     */
    public Coordinate(int column, int row) {
        this.column = column;
        this.row = row;
    }
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    /**
     * Returns the coordinate above the current coordinate.
     *
     * @return A new Coordinate object representing the upper coordinate.
     */
    public Coordinate getUp() {
        return new Coordinate(column, row + 1);
    }

    /**
     * Returns the coordinate to the right of the current coordinate.
     *
     * @return A new Coordinate object representing the right coordinate.
     */
    public Coordinate getRight() {
        return new Coordinate(column + 1, row);
    }

    /**
     * Returns the coordinate below the current coordinate.
     *
     * @return A new Coordinate object representing the lower coordinate.
     */
    public Coordinate getDown() {
        return new Coordinate(column, row - 1);
    }

    /**
     * Returns the coordinate to the left of the current coordinate.
     *
     * @return A new Coordinate object representing the left coordinate.
     */
    public Coordinate getLeft() {
        return new Coordinate(column - 1, row);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) obj;
        return column == that.column && row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "[" + column + ", " + row + ']';
    }
}
