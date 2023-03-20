package it.polimi.ingsw.model;

public class Coordinate {
    private int x;
    private int y;

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

    public Coordinate getUp() {
        return new Coordinate(x, y - 1);
    }

    public Coordinate getRight() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate getDown() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate getLeft() {
        return new Coordinate(x - 1, y);
    }
}
