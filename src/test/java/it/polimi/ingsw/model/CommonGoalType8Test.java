package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType8Test {

    @Test
    public void testCheckGoalTwoRowsReturnsTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, 1));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.book, 1));
        shelf.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.plant, 1));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.trophy, 1));

        CommonGoalType8 goal = new CommonGoalType8();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalOnlyOneRowReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.plant, 0));

        CommonGoalType8 goal = new CommonGoalType8();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalShelfHasLessThan10ObjectCardsReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.plant, 0));

        CommonGoalType8 goal = new CommonGoalType8();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalTwoRowsLessThanFiveTypesReturnsFalse(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.cat, 1));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.book, 1));
        shelf.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.plant, 1));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.trophy, 1));

        CommonGoalType8 goal = new CommonGoalType8();
        assertFalse(goal.checkGoal(shelf));

    }

}