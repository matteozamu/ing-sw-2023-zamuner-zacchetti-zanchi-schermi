package it.polimi.ingsw.model;

public class Shelf {
    private ObjectCard grid[][];
    private Boolean isFull;

    public Shelf(ObjectCard[][] grid, Boolean isFull) {
        this.grid = grid;
        this.isFull = isFull;
    }

    public void addObjectCard(int x, ObjectCard card) {
    }

    public boolean checkFull() {
        return false;
    }

}