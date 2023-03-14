package it.polimi.ingsw.model;

public class Dashboard {
    private ObjectCard grid[][];
    public int ROWS;
    public int COLUMNS;

    public Dashboard(ObjectCard[][] grid, int ROWS, int COLUMNS) {
        this.grid = grid;
        this.ROWS = ROWS;
        this.COLUMNS = COLUMNS;
    }

    public void fillGrid() {
    }

    public ObjectCard getObjectCard(int x, int y) {
        return null;
    }

    public void availableCards() {
    }

}