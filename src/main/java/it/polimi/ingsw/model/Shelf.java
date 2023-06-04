package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO CREAZIONE MAPPA DELLA SHELF SE NO NON POSSIAMO ITERARE OPPURE
//  CAMBiArE IL MODO DI CONTROLLARE IL NUMERO DI RIGHE DISPONIBILI

/**
 * Represents a Shelf in the game, which holds ObjectCards.
 * The origin of the coordinates in the grid is in the lower left corner.
 */
public class Shelf implements Serializable {
    public static final int ROWS = 6;
    public static final int COLUMNS = 5;
    private Map<Coordinate, ObjectCard> grid;
    private boolean isFull;

    /**
     * Constructs a new Shelf.
     */
    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
    }

    /**
     * @return true if the shelf is full
     */
    public boolean isFull() {
        return isFull;
    }

    /**
     * @param full true if a shelf is full
     */
    public void setFull(boolean full) {
        isFull = full;
    }

    /**
     * method that returns the map of the grid
     *
     * @return The grid representing the Shelf
     */
    public Map<Coordinate, ObjectCard> getGrid() {
        return this.grid;
    }

    /**
     * return the number (row) of free cells in the col column
     * if there are no free cells the method throws an exception
     *
     * @param col is the column
     * @return number of free cell for the col column
     */
    public int getFreeCellsPerColumn(int col) {
        int availableRows = this.ROWS;
        Coordinate coordinate;

        for (int row = 0; row < this.ROWS; row++) {
            coordinate = new Coordinate(row, col);
            if (this.grid.containsKey(coordinate) && getObjectCard(coordinate) != null) availableRows--;
        }
        return availableRows;
    }

    public int closeObjectCardsPoints() {
        ObjectCard card;
        int closeCards;
        int points = 0;
        List<ObjectCardType> types = ObjectCardType.VALUES;

        for (ObjectCardType type : types) {
            closeCards = 0;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    card = getObjectCard(new Coordinate(row, col));
                    if (card != null && card.getType().equals(type)) {
                        if (getObjectCard(new Coordinate(row - 1, col)) != null) {
                            if (getObjectCard(new Coordinate(row - 1, col)).getType().equals(card.getType()))
                                closeCards++;
                        } else if (getObjectCard(new Coordinate(row + 1, col)) != null) {
                            if (getObjectCard(new Coordinate(row + 1, col)).getType().equals(card.getType()))
                                closeCards++;
                        } else if (getObjectCard(new Coordinate(row, col - 1)) != null) {
                            if (getObjectCard(new Coordinate(row, col - 1)).getType().equals(card.getType()))
                                closeCards++;
                        } else if (getObjectCard(new Coordinate(row, col + 1)) != null) {
                            if (getObjectCard(new Coordinate(row, col + 1)).getType().equals(card.getType()))
                                closeCards++;
                        }
                    }
                }
            }
            if (closeCards == 3) points += 2;
            if (closeCards == 4) points += 3;
            if (closeCards == 5) points += 5;
            if (closeCards >= 6) points += 8;
        }

        return points;
    }

    /**
     * Returns a map with the number of free cells for each column in the Shelf.
     *
     * @return A Map<Integer, Integer> where the key represents the column index and the value
     * represents the number of free cells in that column.
     */
    public Map<Integer, Integer> getFreeCellsPerColumnMap() {
        Map<Integer, Integer> freeCellsPerColumn = new HashMap<>();

        for (int col = 0; col < COLUMNS; col++) {
            int freeRows = getFreeCellsPerColumn(col);
            freeCellsPerColumn.put(col, freeRows);
        }
        return freeCellsPerColumn;
    }

    /**
     * Returns the ObjectCard at the specified coordinate in the shelf.
     *
     * @param coord The Coordinate object representing the position in the shelf.
     * @return The ObjectCard at the given position, or null if the position is empty.
     */
    public ObjectCard getObjectCard(Coordinate coord) {
        return grid.get(coord);
    }

    @Override
    public String toString() {
        return "Shelf{" + "grid=" + grid + '}';
    }
}