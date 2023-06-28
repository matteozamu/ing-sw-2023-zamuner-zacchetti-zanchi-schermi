package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

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

    private int dfs(ObjectCard currentTile, Set<ObjectCard> visited, ObjectCardType type, int row, int column) {
        visited.add(currentTile);
        int size = 1;
        int[][] DIRECTIONS = {
                {-1, 0}, // Spostamento verso l'alto (riga precedente)
                {1, 0},  // Spostamento verso il basso (riga successiva)
                {0, -1}, // Spostamento verso sinistra (colonna precedente)
                {0, 1}   // Spostamento verso destra (colonna successiva)
        };
        for (int[] dir : DIRECTIONS) {
            int x = row + dir[0];
            int y = column + dir[1];
            Coordinate coordinate = new Coordinate(x, y);
            if (!grid.containsKey(coordinate)) {
                continue; // Proceeding in other adjacent directions
            }
            ObjectCard nextTile = grid.get(coordinate);
            if (!visited.contains(nextTile) && nextTile.getType() == type) {
                size += dfs(nextTile, visited, type, x, y);
            }
        }
        return size;
    }

    public List<Integer> findAdjacentTilesGroups() {
        ObjectCard startingTile = null;
        List<Integer> groupsSizes = new ArrayList<>();
        Set<ObjectCard> visited = new HashSet<>();
        int lastRow = ROWS - 1;
        int lastColumn = COLUMNS - 1;
        int startingRow = lastRow;
        int startingColumn = 0;

        for (Coordinate coordinate : grid.keySet()) {
            int row = coordinate.getRow();
            int column = coordinate.getColumn();
            if (row > lastRow) {
                lastRow = row;
                lastColumn = column;
            }
            ObjectCard currentTile = grid.get(coordinate);
            if (currentTile != null && (!Objects.equals(currentTile.getType(), -1))) {
                startingTile = currentTile;
                startingRow = row;
                startingColumn = column;
                break;
            }
        }

        if (startingTile == null || Objects.equals(startingTile.getType(), -1)) {
            return groupsSizes;
        }

        ObjectCardType startingType = startingTile.getType();
        int groupSize = dfs(startingTile, visited, startingType, startingRow, startingColumn);
        groupsSizes.add(groupSize);

        for (Coordinate coordinate : grid.keySet()) {
            int row = coordinate.getRow();
            int column = coordinate.getColumn();
            ObjectCard currentTile = grid.get(coordinate);
            if (!visited.contains(currentTile) && currentTile.getType() != null && (!Objects.equals(currentTile.getType(), -1))) {
                ObjectCardType currentType = currentTile.getType();
                groupSize = dfs(currentTile, visited, currentType, row, column);
                groupsSizes.add(groupSize);
            }
        }
        return groupsSizes;
    }

    public int closeObjectCardsPoints() {
        int points = 0;

        for (int closeCard : findAdjacentTilesGroups()) {
            if (closeCard == 3) points += 2;
            else if (closeCard == 4) points += 3;
            else if (closeCard == 5) points += 5;
            else if (closeCard >= 6) points += 8;
        }

        return points;
    }

//    public int closeObjectCardsPoints() {
//        ObjectCard card;
//        int closeCards;
//        int points = 0;
//        List<ObjectCardType> types = ObjectCardType.VALUES;
//
//        System.out.println(grid);
//
//        for (ObjectCardType type : types) {
//            closeCards = 0;
//            for (int row = 0; row < ROWS; row++) {
//                for (int col = 0; col < COLUMNS; col++) {
//                    card = getObjectCard(new Coordinate(row, col));
//                    if (card != null && card.getType().equals(type)) {
////                        if (getObjectCard(new Coordinate(row - 1, col)) != null) {
////                            if (getObjectCard(new Coordinate(row - 1, col)).getType().equals(card.getType())) {
////                                System.out.println("row: " + row + " col: " + col + " type: " + card.getType());
////                                closeCards++;
////                            }
////                        }
//                        if (getObjectCard(new Coordinate(row + 1, col)) != null) {
//                            if (getObjectCard(new Coordinate(row + 1, col)).getType().equals(card.getType())) {
//                                System.out.println("row: " + row + " col: " + col + " type: " + card.getType());
//                                closeCards++;
//                            }
//                        }
////                        if (getObjectCard(new Coordinate(row, col - 1)) != null) {
////                            if (getObjectCard(new Coordinate(row, col - 1)).getType().equals(card.getType())) {
////                                System.out.println("row: " + row + " col: " + col + " type: " + card.getType());
////                                closeCards++;
////                            }
////                        }
//                        if (getObjectCard(new Coordinate(row, col + 1)) != null) {
//                            if (getObjectCard(new Coordinate(row, col + 1)).getType().equals(card.getType())) {
//                                System.out.println("row: " + row + " col: " + col + " type: " + card.getType());
//                                closeCards++;
//                            }
//                        }
//
//                        if (getObjectCard(new Coordinate(row, col - 1)) != null && getObjectCard(new Coordinate(row, col - 1)).getType().equals(card.getType())) {
//                            if (getObjectCard(new Coordinate(row, col + 1)) == null || !getObjectCard(new Coordinate(row, col + 1)).getType().equals(card.getType())) {
//                                if (getObjectCard(new Coordinate(row + 1, col)) == null || !getObjectCard(new Coordinate(row + 1, col)).getType().equals(card.getType())) {
//                                    closeCards++;
//                                }
//                            }
//                        }
//
//                        if (getObjectCard(new Coordinate(row - 1, col)) != null && getObjectCard(new Coordinate(row - 1, col)).getType().equals(card.getType())) {
//                            if (getObjectCard(new Coordinate(row, col + 1)) == null || !getObjectCard(new Coordinate(row, col + 1)).getType().equals(card.getType())) {
//                                if (getObjectCard(new Coordinate(row + 1, col)) == null || !getObjectCard(new Coordinate(row + 1, col)).getType().equals(card.getType())) {
//                                    closeCards++;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (closeCards == 3) points += 2;
//            else if (closeCards == 4) points += 3;
//            else if (closeCards == 5) points += 5;
//            else if (closeCards >= 6) points += 8;
//
//            System.out.println(type + ": " + closeCards + " points: " + points);
//        }
//
//        return points;
//    }

//    public int closeObjectCardsPoints() {
//        ObjectCard card;
//        int closeCards;
//        int points = 0;
//        List<ObjectCardType> types = ObjectCardType.VALUES;
//        int[][] visited = new int[ROWS][COLUMNS];
//
//        for (int i = 0; i < ROWS; i++) {
//            for (int j = 0; j < COLUMNS; j++) {
//                visited[i][j] = 0;
//            }
//        }
//
//        for (ObjectCardType type : types) {
//            System.out.println("type: " + type);
//            closeCards = 0;
//            for (int row = 0; row < ROWS; row++) {
//                for (int col = 0; col < COLUMNS; col++) {
//                    System.out.println();
//                    card = getObjectCard(new Coordinate(row, col));
//                    if (card != null && card.getType().equals(type) && visited[row][col] == 0) {
//                        if (getObjectCard(new Coordinate(row - 1, col)) != null && visited[row - 1][col] == 0) {
//                            if (getObjectCard(new Coordinate(row - 1, col)).getType().equals(card.getType())) {
//                                closeCards++;
//                                visited[row][col] = 1;
//                            }
//                        }
//                        if (getObjectCard(new Coordinate(row + 1, col)) != null && visited[row + 1][col] == 0) {
//                            if (getObjectCard(new Coordinate(row + 1, col)).getType().equals(card.getType())) {
//                                closeCards++;
//                                visited[row][col] = 1;
//                            }
//                        }
//                        if (getObjectCard(new Coordinate(row, col - 1)) != null && visited[row][col - 1] == 0) {
//                            if (getObjectCard(new Coordinate(row, col - 1)).getType().equals(card.getType())) {
//                                closeCards++;
//                                visited[row][col] = 1;
//                            }
//                        }
//                        if (getObjectCard(new Coordinate(row, col + 1)) != null && visited[row][col + 1] == 0) {
//                            if (getObjectCard(new Coordinate(row, col + 1)).getType().equals(card.getType())) {
//                                closeCards++;
//                                visited[row][col] = 1;
//                            }
//                        }
//                    }
//                }
//            }
//            if (closeCards == 3) points += 2;
//            else if (closeCards == 4) points += 3;
//            else if (closeCards == 5) points += 5;
//            else if (closeCards >= 6) points += 8;
//        }
//
//        return points;
//    }

    /**
     * Returns a map with the number of free cells for each column in the Shelf.
     *
     * @return A Map<Integer, Integer> where the key represents the column index and the value
     * represents the number of free cells in that column.
     */
//    public Map<Integer, Integer> getFreeCellsPerColumnMap() {
//        Map<Integer, Integer> freeCellsPerColumn = new HashMap<>();
//
//        for (int col = 0; col < COLUMNS; col++) {
//            int freeRows = getFreeCellsPerColumn(col);
//            freeCellsPerColumn.put(col, freeRows);
//        }
//        return freeCellsPerColumn;
//    }

    /**
     * Returns the ObjectCard at the specified coordinate in the shelf.
     *
     * @param coord The Coordinate object representing the position in the shelf.
     * @return The ObjectCard at the given position, or null if the position is empty.
     */
    public ObjectCard getObjectCard(Coordinate coord) {
        return grid.get(coord);
    }

//    @Override
//    public String toString() {
//        return "Shelf{" + "grid=" + grid + '}';
//    }
}