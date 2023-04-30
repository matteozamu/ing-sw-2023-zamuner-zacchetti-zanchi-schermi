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
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.gatto, 2));
        assertTrue(shelf.getGrid().size() < 5);
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalCenterCardIsNull() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.gioco, 2));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.gioco, 0));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.gioco, 1));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.gioco, 2));
        assertFalse(goal.checkGoal(shelf));
    }


    @Test
    public void testCheckGoalCenterCardDifferentType() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.gioco, 2));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.pianta, 2));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.gioco, 0));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.gioco, 1));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.gioco, 2));
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsTrue() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.trofeo, 0));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.trofeo, 1));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.trofeo, 0));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.trofeo, 2));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.trofeo, 1));
        assertTrue(goal.checkGoal(shelf));
    }


}