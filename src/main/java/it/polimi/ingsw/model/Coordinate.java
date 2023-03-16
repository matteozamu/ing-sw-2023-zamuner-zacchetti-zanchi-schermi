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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getU() {
        return new Coordinate(x, y - 1);
    }

    public Coordinate getR() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate getD() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate getL() {
        return new Coordinate(x - 1, y);
    }
}
