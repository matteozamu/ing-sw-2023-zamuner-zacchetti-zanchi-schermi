package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class CommonGoalType2Test {

    @Test
    public void testCheckDiagonalFromTopLeftEmptyShelfReturnsFalse() {
        Shelf emptyShelf = new Shelf();
        Coordinate start = new Coordinate(5, 0);
        ObjectCardType type = ObjectCardType.gatto;

        boolean result = new CommonGoalType2().checkDiagonalFromTopLeft(emptyShelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopLeftHorizontalLineReturnsFalse() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.libro;
        for (int col = 0; col < 5; col++) {
            Coordinate coordinate = new Coordinate(col, 2);
            ObjectCard card = new ObjectCard(type, 0);
            shelf.getGrid().put(coordinate, card);
        }
        Coordinate start = new Coordinate(1, 1);

        boolean result = new CommonGoalType2().checkDiagonalFromTopLeft(shelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopLeftDiagonalLineReturnsTrue() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.trofeo;
        Coordinate start = new Coordinate(5, 0);

        for (int i = 0; i < 5; i++) {
            Coordinate coordinate = new Coordinate(start.getRow() - i, start.getColumn() + i);
            ObjectCard card = new ObjectCard(type, 0);
            shelf.getGrid().put(coordinate, card);
        }

        boolean result = new CommonGoalType2().checkDiagonalFromTopLeft(shelf, start, type);

        assertTrue(result);
    }

    @Test
    public void testCheckDiagonalFromTopRightEmptyShelfReturnsFalse() {
        Shelf emptyShelf = new Shelf();
        Coordinate start = new Coordinate(5, 4);
        ObjectCardType type = ObjectCardType.trofeo;

        boolean result = new CommonGoalType2().checkDiagonalFromTopRight(emptyShelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopRightHorizontalLineReturnsFalse() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.libro;
        for (int col = 0; col < 5; col++) {
            Coordinate coordinate = new Coordinate(col, 2);
            ObjectCard card = new ObjectCard(type, 0);
            shelf.getGrid().put(coordinate, card);
        }
        Coordinate start = new Coordinate(3, 1);

        boolean result = new CommonGoalType2().checkDiagonalFromTopRight(shelf, start, type);

        assertFalse(result);
    }

    @Test
    public void testCheckDiagonalFromTopRightDiagonalLineReturnsTrue() {
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.gatto;
        Coordinate start = new Coordinate(5, 4);

        for (int i = 0; i < 5; i++) {
            Coordinate coordinate = new Coordinate(start.getRow() - i, start.getColumn() - i);
            ObjectCard card = new ObjectCard(type, 0);
            shelf.getGrid().put(coordinate, card);
        }

        boolean result = new CommonGoalType2().checkDiagonalFromTopRight(shelf, start, type);

        assertTrue(result);
    }

    @Test
    public void testCheckGoal () {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.gatto, 2));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.gatto, 2));
        shelf.getGrid().put(new Coordinate(0, 5), new ObjectCard(ObjectCardType.gatto, 1));
        CommonGoalType2 goal = new CommonGoalType2();
        assertTrue(goal.checkGoal(shelf));
    }



}