package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertFalse(this.s.isFull());
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


        //TODO: non passa, errore get(Object)
        @Test
        public void testCloseObjectCardsPoints() {

            // 0 close object cards
            for (int row = 0; row < s.ROWS; row++) {
                for (int col = 0; col < s.COLUMNS; col++) {
                    s.getGrid().put(new Coordinate(row, col), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
                }
            }
            int points = s.closeObjectCardsPoints();
            assertEquals(0, points);

            // 3 close object cards
            ObjectCardType type = ObjectCardType.gatto;
            s.getGrid().put(new Coordinate(1, 1), new ObjectCard(type, 2));
            s.getGrid().put(new Coordinate(1, 2), new ObjectCard(type, 1));
            s.getGrid().put(new Coordinate(1, 3), new ObjectCard(type, 0));
            points = s.closeObjectCardsPoints();
            assertEquals(2, points);

            // 4 close object cards
            s.getGrid().put(new Coordinate(1, 0), new ObjectCard(type, 5));
            points = s.closeObjectCardsPoints();
            assertEquals(3, points);

            // 5 close object cards
            s.getGrid().put(new Coordinate(0, 2), new ObjectCard(type, 6));
            points = s.closeObjectCardsPoints();
            assertEquals(5, points);

            // 6 close object cards
            s.getGrid().put(new Coordinate(1, 2), new ObjectCard(type, 7));
            points = s.closeObjectCardsPoints();
            assertEquals(8, points);

            // 7 close object cards
            s.getGrid().put(new Coordinate(2, 2), new ObjectCard(type, 8));
            points = s.closeObjectCardsPoints();
            assertEquals(8, points);

            // empty shelf
            for (int row = 0; row < s.ROWS; row++) {
                for (int col = 0; col < s.COLUMNS; col++) {
                    s.getGrid().remove(new Coordinate(row, col));
                }
            }
            points = s.closeObjectCardsPoints();
            assertEquals(0, points);
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