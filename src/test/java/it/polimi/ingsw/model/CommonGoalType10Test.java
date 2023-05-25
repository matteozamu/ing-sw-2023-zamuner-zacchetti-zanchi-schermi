package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType10Test {

//    @Test
//    public void testCheckGoalWithNullShelf() {
//        CommonGoalType10 goal = new CommonGoalType10();
//        assertFalse(goal.checkGoal(null));
//    }

    @Test
    public void testCheckGoalEmptyShelf() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalNotEligibleShelf() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.cat, 1));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.cat, 2));
        assertTrue(shelf.getGrid().size() < 5);
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalCenterCardIsNull() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.game, 2));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.game, 1));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.game, 2));
        assertFalse(goal.checkGoal(shelf));
    }


    @Test
    public void testCheckGoalCenterCardDifferentType() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.game, 2));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.plant, 2));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.game, 1));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.game, 2));
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsTrue() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.trophy, 1));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.trophy, 2));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.trophy, 1));
        assertTrue(goal.checkGoal(shelf));
    }


}