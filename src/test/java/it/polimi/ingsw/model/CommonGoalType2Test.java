package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType2Test {

    @Test
    public void testGetType(){
        CommonGoalType2 commonGoalType2 = new CommonGoalType2();
        assertEquals(2, commonGoalType2.getType());
    }

    @Test
    public void testGetDescription(){
        CommonGoalType2 commonGoalType2 = new CommonGoalType2();
        assertEquals("Five tiles of the same type forming a diagonal.", commonGoalType2.getDescription());
    }

    @Test
    public void testGetCardView(){
        CommonGoalType2 commonGoalType2 = new CommonGoalType2();
        assertEquals("""
                ╔══════════╗
                ║■         ║
                ║  ■       ║
                ║    ■     ║
                ║      ■   ║
                ║        ■ ║
                ╚══════════╝
                """, commonGoalType2.getCardView());
    }

    @Test
    public void testToString(){
        CommonGoalType2 commonGoalType2 = new CommonGoalType2();
        assertEquals("commonGoalCard-2", commonGoalType2.toString());
    }


    @Test
    public void testCheckDiagonalFromTopLeftEmptyShelfReturnsFalse() {
        Shelf emptyShelf = new Shelf();
        Coordinate start = new Coordinate(5, 0);
        ObjectCardType type = ObjectCardType.cat;

        boolean result = new CommonGoalType2().checkDiagonalFromTopLeft(emptyShelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopLeftHorizontalLineReturnsFalse() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.book;
        for (int col = 0; col < 5; col++) {
            Coordinate coordinate = new Coordinate(col, 2);
            ObjectCard card = new ObjectCard(type, "00");
            shelf.getGrid().put(coordinate, card);
        }
        Coordinate start = new Coordinate(1, 1);

        boolean result = new CommonGoalType2().checkDiagonalFromTopLeft(shelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopLeftDiagonalLineReturnsTrue() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.trophy;
        Coordinate start = new Coordinate(5, 0);

        for (int i = 0; i < 5; i++) {
            Coordinate coordinate = new Coordinate(start.getRow() - i, start.getColumn() + i);
            ObjectCard card = new ObjectCard(type, "00");
            shelf.getGrid().put(coordinate, card);
        }

        boolean result = new CommonGoalType2().checkDiagonalFromTopLeft(shelf, start, type);

        assertTrue(result);
    }

    @Test
    public void testCheckDiagonalFromTopRightEmptyShelfReturnsFalse() {
        Shelf emptyShelf = new Shelf();
        Coordinate start = new Coordinate(5, 4);
        ObjectCardType type = ObjectCardType.trophy;

        boolean result = new CommonGoalType2().checkDiagonalFromTopRight(emptyShelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopRightHorizontalLineReturnsFalse() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.book;
        for (int col = 0; col < 5; col++) {
            Coordinate coordinate = new Coordinate(col, 2);
            ObjectCard card = new ObjectCard(type, "00");
            shelf.getGrid().put(coordinate, card);
        }
        Coordinate start = new Coordinate(3, 1);

        boolean result = new CommonGoalType2().checkDiagonalFromTopRight(shelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopRightDiagonalLineReturnsTrue() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.cat;
        Coordinate start = new Coordinate(5, 4);

        for (int i = 0; i < 5; i++) {
            Coordinate coordinate = new Coordinate(start.getRow() - i, start.getColumn() - i);
            ObjectCard card = new ObjectCard(type, "00");
            shelf.getGrid().put(coordinate, card);
        }

        boolean result = new CommonGoalType2().checkDiagonalFromTopRight(shelf, start, type);

        assertTrue(result);
    }

    @Test
    public void testCheckGoalReturnsTrue () {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(0, 5), new ObjectCard(ObjectCardType.cat, "10"));
        CommonGoalType2 goal = new CommonGoalType2();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsFalse(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.plant, "10"));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(0, 5), new ObjectCard(ObjectCardType.cat, "10"));
        CommonGoalType2 goal = new CommonGoalType2();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalShelfNotEligible(){
        Shelf shelf = new Shelf();
        CommonGoalType2 goal = new CommonGoalType2();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalFromTopRightReturnsTrue(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(5, 4), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(4, 3), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "20"));
        CommonGoalType2 goal = new CommonGoalType2();
        assertTrue(goal.checkGoal(shelf));
    }


}