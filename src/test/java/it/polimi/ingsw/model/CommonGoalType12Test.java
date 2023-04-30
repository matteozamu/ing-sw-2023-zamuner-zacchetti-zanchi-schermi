package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType12Test {

    @Test
    public void testCheckGoalShelfNotEligible(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        assertFalse(goal.checkGoal(shelf));
    }


//    @Test
//    public void testCheckDescendingStairReturnsTrue(){
//        CommonGoalType12 goal = new CommonGoalType12();
//        Shelf shelf = new Shelf();
//        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(4,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(5,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(4,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//
//        assertTrue(goal.checkDescendingStair(shelf));
//    }

    @Test
    public void testCheckDescendingStairReturnsFalse(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(4,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(5,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(4,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));

        assertFalse(goal.checkDescendingStair(shelf));
    }


    //TODO: expected true returns false
//    @Test
//    public void testCheckAscendingStairReturnsTrue(){
//        CommonGoalType12 goal = new CommonGoalType12();
//        Shelf shelf = new Shelf();
//        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(3,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(4,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(5,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(4,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//
//        assertTrue(goal.checkAscendingStair(shelf));
//    }

    @Test
    public void testCheckAscendingStairReturnsFalse(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(4,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(5,4), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(4,3), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));

        assertFalse(goal.checkAscendingStair(shelf));
    }

}