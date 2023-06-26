package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType11Test {

    @Test
    public void testGetType(){
        CommonGoalType11 goal = new CommonGoalType11();
        assertEquals(11, goal.getType());
    }

    @Test
    public void testGetDescription(){
        CommonGoalType11 goal = new CommonGoalType11();
        assertEquals("Eight tiles of the same type. There’s no\n" +
                "restriction about the position of these tiles.", goal.getDescription());
    }

    @Test
    public void testGetCardView(){
        CommonGoalType11 goal = new CommonGoalType11();
        assertEquals("""
                ╔═════════╗
                ║  ■   ■  ║
                ║  ■ ■ ■  ║
                ║  ■ ■ ■  ║
                ╚═════════╝
                """, goal.getCardView());
    }

    @Test
    public void testCheckGoalNotEligibleShelf(){
        CommonGoalType11 goal = new CommonGoalType11();
        Shelf shelf = new Shelf();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testToString(){
        CommonGoalType11 goal = new CommonGoalType11();
        assertEquals("commonGoalCard-11", goal.toString());
    }

    @Test
    public void testCheckGoalReturnsTrue(){
        CommonGoalType11 goal = new CommonGoalType11();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3,5), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(4,0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.cat, "10"));
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsFalse(){
        CommonGoalType11 goal = new CommonGoalType11();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.trophy, "10"));
        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.trophy, "00"));

        assertFalse(goal.checkGoal(shelf));

    }

}