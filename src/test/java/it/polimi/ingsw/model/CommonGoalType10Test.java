package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType10Test {



    @Test
    public void testCheckGoalWithNullShelf() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();

        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.plant, "10"));

        assertFalse(goal.checkGoal(null));
    }

    @Test
    public void testGetType() {
        CommonGoalType10 goal = new CommonGoalType10();
        assertEquals(10, goal.getType());
    }

    @Test
    public void testGetDescription() {
        CommonGoalType10 goal = new CommonGoalType10();
        assertEquals("Five tiles of the same type forming an X.", goal.getDescription());
    }

    @Test
    public void testGetCardView() {
        CommonGoalType10 goal = new CommonGoalType10();
        assertEquals("""
            ╔═════════╗
            ║  ■   ■  ║
            ║    ■    ║
            ║  ■   ■  ║
            ╚═════════╝
            """, goal.getCardView());
    }

    @Test
    public void testToString() {
        CommonGoalType10 goal = new CommonGoalType10();
        assertEquals("commonGoalCard-10", goal.toString());
    }

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
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.cat, "20"));
        assertTrue(shelf.getGrid().size() < 5);
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalCenterCardIsNull() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.game, "20"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.game, "10"));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.game, "20"));
        shelf.getGrid().put(new Coordinate(4,4), new ObjectCard(ObjectCardType.game, "20"));

        assertFalse(goal.checkGoal(shelf));
    }


    @Test
    public void testCheckGoalCenterCardDifferentType() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.game, "20"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.game, "10"));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.game, "20"));
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsTrue() {
        CommonGoalType10 goal = new CommonGoalType10();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.trophy, "10"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.trophy, "20"));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.trophy, "10"));
        assertTrue(goal.checkGoal(shelf));
    }


}