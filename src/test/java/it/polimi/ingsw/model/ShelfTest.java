package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ShelfTest extends TestCase {
    private Shelf s;
    private ObjectCard oc;

    @BeforeEach
    public void setUp() {
        s = new Shelf();
        oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 1);
    }


    @Test
    public void testAvailableRowsEmpty() {
        assertFalse(this.s.getFull());
        assertEquals(6, this.s.getAvailableRows(0));
    }

    @Test
    public void testAvailableRows() {
        int cards = 4;
        for (int row = 0; row < cards; row++) {
            this.oc = new ObjectCard(ObjectCardType.gatto, 0);
            s.getGrid().put(new Coordinate(row, 0), this.oc);
        }
        assertEquals(6-cards, this.s.getAvailableRows(0));
    }

//    @Test
//    public void testCheckFull() {
//        for (int row = 0; row < s.ROWS; row++) {
//            for (int col = 0; col < s.COLUMNS; col++) {
//                this.oc = new ObjectCard(ObjectCardType.gatto, 0);
//                s.getGrid().put(new Coordinate(row, col), this.oc);
//            }
//        }
//        // Verifica che la Shelf sia piena.
//        assertTrue(s.getFull());
//    }

}