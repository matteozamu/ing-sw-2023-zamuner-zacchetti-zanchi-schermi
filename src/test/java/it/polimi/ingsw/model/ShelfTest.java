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

        @Test
        //TODO: da rivedere il test. Il metodo è ok
        public void testCloseObjectCardsPoints() {
            // 0 close object cards
            for (int row = 0; row < s.ROWS; row++) {
                for (int col = 0; col < s.COLUMNS; col++) {
                    s.getGrid().put(new Coordinate(row, col), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
                }
            }
            assertEquals(0, s.closeObjectCardsPoints());

            // 3 close object cards
            ObjectCardType type = ObjectCardType.gatto;
            s.getGrid().put(new Coordinate(1, 1), new ObjectCard(type, 2));
            s.getGrid().put(new Coordinate(1, 2), new ObjectCard(type, 1));
            s.getGrid().put(new Coordinate(1, 3), new ObjectCard(type, 0));
            assertEquals(2, s.closeObjectCardsPoints());

            // 4 close object cards
            s.getGrid().put(new Coordinate(1, 0), new ObjectCard(type, 2));
            assertEquals(3, s.closeObjectCardsPoints());

            // 5 close object cards
            s.getGrid().put(new Coordinate(0, 2), new ObjectCard(type, 1));
            assertEquals(5, s.closeObjectCardsPoints());

            // 6 close object cards
            s.getGrid().put(new Coordinate(1, 2), new ObjectCard(type, 0));
            assertEquals(8, s.closeObjectCardsPoints());

            // 7 close object cards
            s.getGrid().put(new Coordinate(2, 2), new ObjectCard(type, 1));
            assertEquals(8, s.closeObjectCardsPoints());

            // empty shelf
            for (int row = 0; row < s.ROWS; row++) {
                for (int col = 0; col < s.COLUMNS; col++) {
                    s.getGrid().remove(new Coordinate(row, col));
                }
            }
            assertEquals(0, s.closeObjectCardsPoints());
        }
}