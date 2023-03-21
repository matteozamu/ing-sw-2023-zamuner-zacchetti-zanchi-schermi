package it.polimi.ingsw.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Shelf {
    private Map<Coordinate, ObjectCard> grid;
    private Boolean isFull;
    private int numberOfCards;
    private final int ROWS = 6;
    private final int COLUMNS = 5;

    public Shelf() {
        this.grid = new HashMap<>();
        this.isFull = false;
        this.numberOfCards = 0;
    }

    /**
     * Metodo che data una colonna x ritorna il numero y di celle libere nella colonna. Nel caso in cui non c'è nessuna cella libera ritorna -1
     * @param x is the column
     * @return number of free cell
     */
    public int getNextAvailableRow(int x) {
        for (int y = 5; y >= 0; y--) {
            Coordinate coordinate = new Coordinate(x, y);
            if (grid.get(coordinate) == null) {
                return y; //TODO : forse è y-1
            }
        }
        return -1;
    }

    /**
     * Metodo per aggiungere una carta oggetto nella prima cella libera della colonna x
     * @param x
     * @param card
     */
    public void addObjectCard(int x, ObjectCard card) {
        //TODO: Da revisionare (far sapere a chi chiama se la carta oggetto è stata correttamente aggiunta. Non aggiungere carte se non c'è spazio nella shelf)
        int y = getNextAvailableRow(x);
        if (y != -1) {
            grid.put(new Coordinate(x, y), card);
            numberOfCards++;
            if (numberOfCards == ROWS * COLUMNS) {
                isFull = true;
            }
        }
    }

    public boolean checkFull() {
        return isFull;
    }

    /**
     * Metodo che ritorna la mappa che rappresenta la griglia della shelf
     * @return
     */
    public Map<Coordinate, ObjectCard> getGrid() {
        return Collections.unmodifiableMap(grid);
    }

    /**
     * Metodo che data una coppia di coordinate "coord" ritorna la carta oggetto presente nella cella della shelf di coordinate "coord"
     * @param coord
     * @return
     */
    public ObjectCard getObjectCard(Coordinate coord) {
        return grid.get(coord);
    }

}