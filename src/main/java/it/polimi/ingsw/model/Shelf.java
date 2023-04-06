package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Shelf in the game, which holds ObjectCards.
 * The origin of the coordinates in the grid is in the lower left corner.
 */
public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private boolean isFull;
    public final int ROWS;
    public final int COLUMNS;

    /**
     * Constructs a new Shelf.
     */
    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;

        //TODO: in quanto costanti non è meglio inizializzarle quando le si dichiara?
        this.ROWS = 6;
        this.COLUMNS = 5;
    }

    /**
     *
     * @return true if the shelf is full
     */
    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    /**
     * method that returns the map of the grid
     * @return The grid representing the Shelf
     */
    public Map<Coordinate, ObjectCard> getGrid() {
        return this.grid;
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

    /**
     * return the number (row) of free cells in the col column
     * if there are no free cells the method throws an exception
     * @param col is the column
     * @return number of free cell for the col column
     */
    public int getAvailableRows(int col) {
        int availableRows = this.ROWS;
        Coordinate coord;

        for (int row = 0; row < this.ROWS; row++) {
            coord = new Coordinate(row, col);
            if (this.grid.containsKey(coord) && getObjectCard(coord) != null) {
                availableRows--;
            }
        }
        return availableRows;
    }

    /**
     * Calculates and returns the total points earned for ObjectCards that are adjacent
     * to each other with the same type. Points are awarded based on the number of
     * adjacent cards with the same type.
     *
     * - 3 adjacent cards: 2 points
     * - 4 adjacent cards: 3 points
     * - 5 adjacent cards: 5 points
     * - 6 or more adjacent cards: 8 points
     *
     * The method iterates through each ObjectCardType and for each type, it checks
     * for adjacent cards with the same type in the Shelf grid.
     *
     * @return The total points earned for adjacent ObjectCards with the same type.
     */
    public int closeObjectCardsPoints() {
        ObjectCard card;
        int closeCards;
        int points = 0;
        List<ObjectCardType> types = ObjectCardType.VALUES;

        for(ObjectCardType type : types) {
            closeCards = 0;
            for (int row = 0; row < this.ROWS; row++) {
                for (int col = 0; col < this.COLUMNS; col++) {
                    card = getObjectCard(new Coordinate(row, col));
                    if (card != null && card.getType().equals(type)) {
                        if (getObjectCard(new Coordinate(row - 1, col)) != null){
                            if(getObjectCard(new Coordinate(row - 1, col)).getType().equals(card.getType())) {
                                closeCards++;
                            }
                        }
                        else if (getObjectCard(new Coordinate(row + 1, col)) != null){
                            if(getObjectCard(new Coordinate(row + 1, col)).getType().equals(card.getType())) {
                                closeCards++;
                            }
                        }
                        else if (getObjectCard(new Coordinate(row, col - 1)) != null){
                            if(getObjectCard(new Coordinate(row, col - 1)).getType().equals(card.getType())) {
                                closeCards++;
                            }
                        }
                        else if (getObjectCard(new Coordinate(row, col + 1)) != null){
                            if(getObjectCard(new Coordinate(row, col + 1)).getType().equals(card.getType())) {
                                closeCards++;
                            }
                        }
                    }
                }
            }
            if(closeCards == 3) points += 2;
            if(closeCards == 4) points += 3;
            if(closeCards == 5) points += 5;
            if(closeCards >= 6) points += 8;
        }

        return points;
    }

    /**
     * Prints the layout of object cards on the Shelf, including both the type and ID of each card.
     * Cards are printed in reverse row order, starting from the last row and proceeding towards the first.
     * Each card is represented as "type-id", where "type" is the card type and "id" is its ID.
     * In case a cell is empty, a placeholder with dashes ("-") will be printed.
     */
    public void printShelf() {
        int maxLengthType = 9;

        for (int row = this.ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < this.COLUMNS; col++) {
                Coordinate coord = new Coordinate(row, col);
                ObjectCard card = getObjectCard(coord);
                if (card == null) {
                    System.out.print("-".repeat(maxLengthType));
                } else {
                    String cardText = card.toString();
                    int padding = maxLengthType - cardText.length();
                    System.out.print(cardText + " ".repeat(padding));
                }
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    // TODO CI SERVE? Si
    /**
     * Returns a map with the number of free cells for each column in the Shelf.
     *
     * @return A Map<Integer, Integer> where the key represents the column index and the value
     * represents the number of free cells in that column.
     */
    public Map<Integer, Integer> getFreeCellsPerColumn() {
        Map<Integer, Integer> freeCellsPerColumn = new HashMap<>();

        for (int col = 0; col < COLUMNS; col++) {
            int freeRows = getAvailableRows(col);
            freeCellsPerColumn.put(col, freeRows);
        }
        return freeCellsPerColumn;
    }

    @Override
    public String toString() {
        return "Shelf{" + "grid=" + grid + '}';
    }
}