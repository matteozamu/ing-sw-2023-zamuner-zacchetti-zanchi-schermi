package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType8Test {

    @Test
    public void testToString(){
        CommonGoalType8 commonGoalType8 = new CommonGoalType8();
        assertEquals("commonGoalCard-8", commonGoalType8.toString());
    }

    @Test
    public void testGetType(){
        CommonGoalType8 commonGoalType8 = new CommonGoalType8();
        assertEquals(8, commonGoalType8.getType());
    }

    @Test
    public void testGetDescription(){
        CommonGoalType8 commonGoalType8 = new CommonGoalType8();
        assertEquals("Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.", commonGoalType8.getDescription());
    }

    @Test
    public void testGetCardView(){
        CommonGoalType8 commonGoalType8 = new CommonGoalType8();
        assertEquals("""
                ╔═══════════╗
                ║           ║
                ║ ■ ■ ■ ■ ■ ║
                ║    x2     ║
                ║           ║
                ║           ║
                ╚═══════════╝
                """, commonGoalType8.getCardView());
    }

    @Test
    public void testCheckGoalTwoRowsReturnsTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.book, "10"));
        shelf.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.plant, "10"));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.trophy, "10"));

        CommonGoalType8 goal = new CommonGoalType8();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalOnlyOneRowReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.plant, "00"));

        CommonGoalType8 goal = new CommonGoalType8();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalShelfHasLessThan10ObjectCardsReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.plant, "00"));

        CommonGoalType8 goal = new CommonGoalType8();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalTwoRowsLessThanFiveTypesReturnsFalse(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.book, "10"));
        shelf.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.plant, "10"));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.trophy, "10"));

        CommonGoalType8 goal = new CommonGoalType8();
        assertFalse(goal.checkGoal(shelf));

    }

}