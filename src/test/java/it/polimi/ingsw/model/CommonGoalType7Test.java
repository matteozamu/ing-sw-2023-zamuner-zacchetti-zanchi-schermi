package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType7Test {

    @Test
    public void testToString(){
        CommonGoalType7 commonGoalType7 = new CommonGoalType7();
        assertEquals("commonGoalCard-7", commonGoalType7.toString());
    }

    @Test
    public void testGetType(){
        CommonGoalType7 commonGoalType7 = new CommonGoalType7();
        assertEquals(7, commonGoalType7.getType());
    }

    @Test
    public void testGetDescription(){
        CommonGoalType7 commonGoalType7 = new CommonGoalType7();
        assertEquals("""
            Two groups each containing 4 tiles of
            the same type in a 2x2 square. The tiles
            of one square can be different from
            those of the other square.""", commonGoalType7.getDescription());
    }

    @Test
    public void testGetCardView(){
        CommonGoalType7 commonGoalType7 = new CommonGoalType7();
        assertEquals("""
            ╔═══════╗
            ║  ■ ■  ║
            ║  ■ ■  ║
            ║  x2   ║
            ╚═══════╝
            """, commonGoalType7.getCardView());

    }

    @Test
    public void testIsSquareWithSquare() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, "20"));
        CommonGoalType7 goal = new CommonGoalType7();
        assertTrue(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.cat));
    }

    @Test
    public void testIsSquareWithNonSquare() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.book, "00"));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.cat));
    }

    @Test
    public void testIsSquareWithMissingCard() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "00"));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.cat));
    }

    @Test
    public void testIsSquareBottomLeftNull(){
        Shelf shelf = new Shelf();
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.cat));
    }

    @Test
    public void testIsSquareBottomLeftWrongType(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.book, "00"));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.cat));

    }

    @Test
    public void testCheckGoalReturnsFalseIfShelfIsNotEligible() {
        Shelf shelf = new Shelf();
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalOnlyOneSquareReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.trophy, "10"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.trophy, "20"));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalTwoSquaresReturnsTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.game, "10"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, "20"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.game, "20"));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.game, "20"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.game, "10"));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.game, "20"));
        CommonGoalType7 goal = new CommonGoalType7();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    void testCheckGoalTwoSquaresDifferentTypesReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.trophy, "10"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.trophy, "20"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.plant, "10"));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.plant, "00"));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.checkGoal(shelf));
    }
}