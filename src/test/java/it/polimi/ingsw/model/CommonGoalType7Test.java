package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType7Test {

    @Test
    public void testIsSquareWithSquare() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, 1));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, 2));
        CommonGoalType7 goal = new CommonGoalType7();
        assertTrue(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.cat));
    }

    @Test
    public void testIsSquareWithNonSquare() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 1));
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.book, 0));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.isSquare(shelf, new Coordinate(0, 0), ObjectCardType.cat));
    }

    @Test
    public void testIsSquareWithMissingCard() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, 1));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, 0));
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
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.book, 0));
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
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.trophy, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.trophy, 2));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalTwoSquaresReturnsTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.game, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.game, 2));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.game, 2));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.game, 1));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.game, 2));
        CommonGoalType7 goal = new CommonGoalType7();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    void testCheckGoalTwoSquaresDifferentTypesReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.trophy, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.trophy, 2));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.plant, 1));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.plant, 2));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.plant, 0));
        CommonGoalType7 goal = new CommonGoalType7();
        assertFalse(goal.checkGoal(shelf));
    }
}