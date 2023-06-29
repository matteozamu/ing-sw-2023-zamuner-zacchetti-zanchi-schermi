package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType9Test {

    @Test
    public void testToString() {
        CommonGoalType9 commonGoalType9 = new CommonGoalType9();
        assertEquals("commonGoalCard-9", commonGoalType9.toString());
    }

    @Test
    public void testGetType() {
        CommonGoalType9 commonGoalType9 = new CommonGoalType9();
        assertEquals(9, commonGoalType9.getType());
    }

    @Test
    public void testGetDescription() {
        CommonGoalType9 commonGoalType9 = new CommonGoalType9();
        assertEquals("""
                Three columns each formed by 6 tiles 
                of maximum three different types. One
                column can show the same or a different
                combination of another column.""", commonGoalType9.getDescription());
    }

    @Test
    public void testGetCardView() {
        CommonGoalType9 commonGoalType9 = new CommonGoalType9();
        assertEquals("""
                ╔═════════════╗
                ║      ■      ║
                ║      ■      ║
                ║      ■      ║
                ║      ■  x3  ║
                ║      ■      ║
                ║      ■      ║
                ╚═════════════╝
                """, commonGoalType9.getCardView());
    }

    @Test
    public void testCheckGoalShelfNotEligible() {
        Shelf shelf = new Shelf();
        CommonGoalType9 goal = new CommonGoalType9();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalThreeColumnsReturnsTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(4, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.cat, "00"));

        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(5, 1), new ObjectCard(ObjectCardType.plant, "00"));

        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(5, 2), new ObjectCard(ObjectCardType.trophy, "00"));

        CommonGoalType9 goal = new CommonGoalType9();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalOnlyTwoColumnsReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(5, 1), new ObjectCard(ObjectCardType.plant, "00"));

        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(5, 2), new ObjectCard(ObjectCardType.trophy, "00"));

        CommonGoalType9 goal = new CommonGoalType9();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalThreeColumnsOneWrongReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(4, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.cat, "00"));

        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(5, 1), new ObjectCard(ObjectCardType.plant, "00"));

        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(5, 2), new ObjectCard(ObjectCardType.cat, "00"));

        CommonGoalType9 goal = new CommonGoalType9();
        assertFalse(goal.checkGoal(shelf));

    }

}