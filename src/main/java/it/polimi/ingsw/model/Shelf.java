package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// TODO CREAZIONE MAPPA DELLA SHELF SE NO NON POSSIAMO ITERARE OPPURE
//  CAMBiArE IL MODO DI CONTROLLARE IL NUMERO DI RIGHE DISPONIBILI

/**
 * Represents a Shelf in the game, which holds ObjectCards.
 * The origin of the coordinates in the grid is in the lower left corner.
 */
public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private boolean isFull;
//    public int numberOfCards;
    public final int ROWS;
    public final int COLUMNS;

    /**
     * Constructs a new Shelf.
     */
    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
//        this.numberOfCards = 0;

        //TODO: in quanto costanti non è meglio inizializzarle quando le si dichiara?
        this.ROWS = 6;
        this.COLUMNS = 5;
    }

//    public int getNumberOfCards() {
//        return numberOfCards;
//    }

//    public void setNumberOfCards(int numberOfCards) {
//        this.numberOfCards = numberOfCards;
//    }

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
     * Prints the layout of object cards on the Shelf, including both the type and ID of each card.
     * Cards are printed in reverse row order, starting from the last row and proceeding towards the first.
     * Each card is represented as "type-id", where "type" is the card type and "id" is its ID.
     * In case a cell is empty, a placeholder with dashes ("-") will be printed.
     */
    public void printShelf() {
        int maxLength = Arrays.stream(ObjectCardType.values())
                .map(ObjectCardType::toString)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        maxLength += 2; // Adds space for ID and separator "-"

        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLUMNS; col++) {
                Coordinate coord = new Coordinate(row, col);
                ObjectCard card = getObjectCard(coord);
                if (card == null) {
                    System.out.print("-".repeat(maxLength));
                } else {
                    String cardText = card.toString();
                    int padding = maxLength - cardText.length();
                    System.out.print(cardText + " ".repeat(padding));
                }
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    /**
     * return the number (row) of free cells in the col column
     * if there are no free cells the method throws an exception
     * @param col is the column
     * @return number of free cell for the col column
     */
    public int getAvailableRows(int col) {
        int availableRows = ROWS;
        Coordinate coordinate;

        for (int row = 0; row < this.ROWS; row++) {
            coordinate = new Coordinate(row, col);
            if (this.grid.containsKey(coordinate) && this.grid.get(coordinate) != null) availableRows--;
        }
        return availableRows;
    }

    // TODO CI SERVE? Spoiler: no. Ma se serve c'è. Decisamente MDR
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