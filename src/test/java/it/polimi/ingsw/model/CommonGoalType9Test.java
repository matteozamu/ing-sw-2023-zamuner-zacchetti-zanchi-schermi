package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType9Test {

    @Test
    public void testCheckGoalShelfNotEligible(){
        Shelf shelf = new Shelf();
        CommonGoalType9 goal = new CommonGoalType9();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalThreeColumnsReturnsTrue(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(3, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(4, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.cat, 0));

        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(5, 1), new ObjectCard(ObjectCardType.plant, 0));

        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(5, 2), new ObjectCard(ObjectCardType.trophy, 0));

        CommonGoalType9 goal = new CommonGoalType9();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalOnlyTwoColumnsReturnsFalse(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(5, 1), new ObjectCard(ObjectCardType.plant, 0));

        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(5, 2), new ObjectCard(ObjectCardType.trophy, 0));

        CommonGoalType9 goal = new CommonGoalType9();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalThreeColumnsOneWrongReturnsFalse(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(3, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(4, 0), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.cat, 0));

        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(2, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(3, 1), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(4, 1), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(5, 1), new ObjectCard(ObjectCardType.plant, 0));

        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(3, 2), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(4, 2), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(5, 2), new ObjectCard(ObjectCardType.cat, 0));

        CommonGoalType9 goal = new CommonGoalType9();
        assertFalse(goal.checkGoal(shelf));

    }

}