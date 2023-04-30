package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType11Test {

    @Test
    public void testCheckGoalNotEligibleShelf(){
        CommonGoalType11 goal = new CommonGoalType11();
        Shelf shelf = new Shelf();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsTrue(){
        CommonGoalType11 goal = new CommonGoalType11();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.gatto, 2));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(3,5), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.gatto, 2));
        shelf.getGrid().put(new Coordinate(4,0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.gatto, 1));
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsFalse(){
        CommonGoalType11 goal = new CommonGoalType11();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.gatto, 2));
        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.pianta, 0));
        assertFalse(goal.checkGoal(shelf));

    }

}