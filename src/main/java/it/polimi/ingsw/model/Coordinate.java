package it.polimi.ingsw.model;

import java.util.Objects;

public class Coordinate {
    private int x; //row
    private int y;  //column

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //TODO rivedere le get
    /**
     * Method returning the upper coordinate pair
     * @return Coordinate
     */
    public Coordinate getUp() {
        return new Coordinate(x, y - 1);
    }

    /**
     * Method returning the right coordinate pair
     * @return Coordinate
     */
    public Coordinate getRight() {
        return new Coordinate(x + 1, y);
    }

    /**
     * Method returning the lower coordinate pair
     * @return Coordinate
     */
    public Coordinate getDown() {
        return new Coordinate(x, y + 1);
    }

    /**
     * Method returning the left coordinate pair
     * @return Coordinate
     */
    public Coordinate getLeft() {
        return new Coordinate(x - 1, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ']';
    }
}
