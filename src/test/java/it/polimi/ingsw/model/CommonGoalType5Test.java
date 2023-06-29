package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType5Test {

    @Test
    public void testToString() {
        CommonGoalType5 commonGoalType5 = new CommonGoalType5();
        assertEquals("commonGoalCard-5", commonGoalType5.toString());
    }

    @Test
    public void testGetType() {
        CommonGoalType5 commonGoalType5 = new CommonGoalType5();
        assertEquals(5, commonGoalType5.getType());
    }

    @Test
    public void testGetDescription() {
        CommonGoalType5 commonGoalType5 = new CommonGoalType5();
        assertEquals("""
                Four groups each containing at least
                4 tiles of the same type (not necessarily
                in the depicted shape).
                The tiles of one group can be different
                from those of another group.""", commonGoalType5.getDescription());
    }

    @Test
    public void testGetCardView() {
        CommonGoalType5 commonGoalType5 = new CommonGoalType5();
        assertEquals("""
                ╔═══════════╗
                ║     ■     ║
                ║     ■     ║
                ║     ■     ║
                ║     ■     ║
                ║     x4    ║
                ╚═══════════╝
                """, commonGoalType5.getCardView());
    }

    @Test
    public void testCheckGoalTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.plant, "10"));
        shelf.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.plant, "00"));

        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.cat, "00"));

        shelf.getGrid().put(new Coordinate(3, 0), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.book, "10"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.book, "20"));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.book, "00"));

        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.trophy, "10"));
        shelf.getGrid().put(new Coordinate(2, 4), new ObjectCard(ObjectCardType.trophy, "20"));
        shelf.getGrid().put(new Coordinate(3, 4), new ObjectCard(ObjectCardType.trophy, "00"));

        CommonGoalType5 goal = new CommonGoalType5();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalNotEligibleShelf() {
        Shelf shelf = new Shelf();
        assertFalse(new CommonGoalType5().checkGoal(shelf));
    }

    @Test
    public void testCheckGoalNoValidGroupsReturnsFalse() {
        Shelf shelf = new Shelf();

        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(3, 0), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(4, 0), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.frame, "00"));

        assertFalse(new CommonGoalType5().checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsTrue() {
        Shelf shelf = new Shelf();

        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.book, "00"));

        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(2, 4), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(3, 4), new ObjectCard(ObjectCardType.plant, "00"));

        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.cat, "00"));

        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.frame, "00"));

        assertTrue(new CommonGoalType5().checkGoal(shelf));
    }
}