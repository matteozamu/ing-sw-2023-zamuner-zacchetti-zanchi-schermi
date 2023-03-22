package it.polimi.ingsw.model;

import java.util.Objects;

//TODO : siccome la Shelf e la Board hanno due sistemi di riferimento differenti
// conviene creare due classi figlie della classe Coordinate (ad esempio ShelfCoordinate e BoardCoordinate).
// Per poter gestire più facilmente i casi in cui ci si muove con le coordinate al di fuori dei limiti della Shelf o della Board.

/**
 * Represents a 2D coordinate in the game grid.
 */
public class Coordinate {
    private int column;
    private int row;

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
     * Method returning the upper coordinate pair
     * @return Coordinate
     */
    public Coordinate getUp() {
        return new Coordinate(column, row + 1);
    }

    /**
     * Method returning the right coordinate pair
     * @return Coordinate
     */
    public Coordinate getRight() {
        return new Coordinate(column + 1, row);
    }

    /**
     * Method returning the lower coordinate pair
     * @return Coordinate
     */
    public Coordinate getDown() {
        return new Coordinate(column, row - 1);
    }

    /**
     * Method returning the left coordinate pair
     * @return Coordinate
     */
    public Coordinate getLeft() {
        return new Coordinate(column - 1, row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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
