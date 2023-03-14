package it.polimi.ingsw.model;

public class Shelf {
    private ObjectCard grid[][];
    private Boolean isFull;
    private int numbersOfCards;

    public Shelf(ObjectCard[][] grid, Boolean isFull, int numbersOfCards) {
        this.grid = grid;
        this.isFull = isFull;
        this.numbersOfCards = numbersOfCards;
    }

    public void addObjectCard(int x, ObjectCard card) {
    }

    public boolean checkFull() {
        return false;
    }

}