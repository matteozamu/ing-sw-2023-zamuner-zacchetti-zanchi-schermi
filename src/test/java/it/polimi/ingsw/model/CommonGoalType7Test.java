package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType7Test {

    @Test
    public void testIsSquareWithSquare() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.gatto, 2));
        CommonGoalType7 goal = new CommonGoalType7();
        assertTrue(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.gatto));
    }

    @Test
    public void testIsSquareWithNonSquare() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.libro, 0));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.libro, 0));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.gatto));
    }

    @Test
    public void testIsSquareWithMissingCard() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.gatto, 0));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.gatto));
    }

    @Test
    public void testIsSquareBottomLeftNull(){
        Shelf shelf = new Shelf();
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.gatto));
    }

    @Test
    public void testIsSquareBottomLeftWrongType(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.libro, 0));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.gatto));

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
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.trofeo, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.trofeo, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.trofeo, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.trofeo, 2));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalTwoSquaresReturnsTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.gioco, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.gioco, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.gioco, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.gioco, 2));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.gioco, 2));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.gioco, 1));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.gioco, 0));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.gioco, 2));
        CommonGoalType7 goal = new CommonGoalType7();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    void testCheckGoalTwoSquaresDifferentTypesReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.trofeo, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.trofeo, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.trofeo, 2));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.trofeo, 0));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.pianta, 0));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.pianta, 1));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.pianta, 2));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.pianta, 0));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.checkGoal(shelf));
    }
}