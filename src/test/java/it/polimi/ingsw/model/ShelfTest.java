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
        oc = new ObjectCard(ObjectCardType.randomObjectCardType(), "10");
    }


    @Test
    public void testAvailableRowsEmpty() {
        assertFalse(this.s.isFull());
        assertEquals(6, this.s.getFreeCellsPerColumn(0));
    }

    @Test
    public void testAvailableRows() {
        int cards = 4;
        for (int row = 0; row < cards; row++) {
            this.oc = new ObjectCard(ObjectCardType.cat, "00");
            s.getGrid().put(new Coordinate(row, 0), this.oc);
        }
        assertEquals(6 - cards, this.s.getFreeCellsPerColumn(0));
    }

    @Test
    public void testCloseObjectCardsPointsNoCardsClose() {
        // 0 close object cards
        assertEquals(0, s.closeObjectCardsPoints());

        s.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, "20"));
        s.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.book, "10"));
        s.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.cat, "00"));

        assertEquals(2, s.closeObjectCardsPoints());
    }

    @Test
    public void testCloseObjectCardsPoints3() {
        s.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "01"));
        s.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, "02"));

        assertEquals(2, s.closeObjectCardsPoints());
    }

    @Test
    public void testCloseObjectCardsPointsL() {
        s.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.cat, "00"));

        s.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.trophy, "02"));
        s.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.trophy, "02"));

        s.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.game, "02"));
        s.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.game, "02"));
        s.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.game, "02"));
        s.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.game, "02"));
        s.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.game, "02"));
        s.getGrid().put(new Coordinate(2, 4), new ObjectCard(ObjectCardType.game, "02"));

        assertEquals(10, s.closeObjectCardsPoints());
    }

    @Test
    public void testCloseObjectCardsPointsTwoSquaresOfFour() {
        s.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, "10"));
        s.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, "20"));

        s.getGrid().put(new Coordinate(4, 3), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(5, 3), new ObjectCard(ObjectCardType.cat, "10"));
        s.getGrid().put(new Coordinate(4, 4), new ObjectCard(ObjectCardType.cat, "00"));
        s.getGrid().put(new Coordinate(5, 4), new ObjectCard(ObjectCardType.cat, "20"));

        assertEquals(6, s.closeObjectCardsPoints());
    }

    @Test
    public void testCloseObjectCardsPoints() {
        // 3 close object cards
        ObjectCardType type = ObjectCardType.cat;
        s.getGrid().put(new Coordinate(1, 1), new ObjectCard(type, "20"));
        s.getGrid().put(new Coordinate(1, 2), new ObjectCard(type, "10"));
        s.getGrid().put(new Coordinate(1, 3), new ObjectCard(type, "00"));
        assertEquals(2, s.closeObjectCardsPoints());

        // 4 close object cards
        s.getGrid().put(new Coordinate(1, 0), new ObjectCard(type, "20"));
        assertEquals(3, s.closeObjectCardsPoints());

        // 5 close object cards
        s.getGrid().put(new Coordinate(0, 2), new ObjectCard(type, "10"));
        assertEquals(5, s.closeObjectCardsPoints());

        // 6 close object cards
        s.getGrid().put(new Coordinate(1, 4), new ObjectCard(type, "00"));
        assertEquals(8, s.closeObjectCardsPoints());

        // 7 close object cards
        s.getGrid().put(new Coordinate(2, 2), new ObjectCard(type, "10"));
        assertEquals(8, s.closeObjectCardsPoints());
    }
}