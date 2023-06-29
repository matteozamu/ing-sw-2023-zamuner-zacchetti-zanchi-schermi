package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.*;

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

    /**
     * Depth-first search (DFS) is an algorithm for traversing or searching tree or graph data structures.
     * The algorithm starts at the root node (selecting some arbitrary node as the root node in the case of a graph)
     * and explores as far as possible along each branch before backtracking.
     * It is a recursive algorithm used for the calculation of the points.
     */
    private int dfs(ObjectCard currentTile, Set<ObjectCard> visited, ObjectCardType type, int row, int column) {
        visited.add(currentTile);
        int size = 1;
        int[][] DIRECTIONS = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };
        for (int[] dir : DIRECTIONS) {
            int x = row + dir[0];
            int y = column + dir[1];
            Coordinate coordinate = new Coordinate(x, y);
            if (!grid.containsKey(coordinate)) {
                continue;
            }
            ObjectCard nextTile = grid.get(coordinate);
            if (!visited.contains(nextTile) && nextTile.getType() == type) {
                size += dfs(nextTile, visited, type, x, y);
            }
        }
        return size;
    }

    /**
     * finds groups of adjacent ObjectCards of the same type.
     * It is used for the calculation of the points.
     */
    public List<Integer> findAdjacentTilesGroups() {
        ObjectCard startingTile = null;
        List<Integer> groupsSizes = new ArrayList<>();
        Set<ObjectCard> visited = new HashSet<>();
        int lastRow = ROWS - 1;
        int startingRow = lastRow;
        int startingColumn = 0;

        for (Coordinate coordinate : grid.keySet()) {
            int row = coordinate.getRow();
            int column = coordinate.getColumn();
            if (row > lastRow) {
                lastRow = row;
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

    /**
     * Calculates the point from the groups of adjacent ObjectCards.
     */
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

    /**
     * Returns the ObjectCard at the specified coordinate in the shelf.
     *
     * @param coord The Coordinate object representing the position in the shelf.
     * @return The ObjectCard at the given position, or null if the position is empty.
     */
    public ObjectCard getObjectCard(Coordinate coord) {
        return grid.get(coord);
    }
}