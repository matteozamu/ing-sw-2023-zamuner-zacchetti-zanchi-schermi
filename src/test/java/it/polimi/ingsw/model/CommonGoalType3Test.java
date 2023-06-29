package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType3Test {

    @Test
    public void testGetType(){
        CommonGoalType3 goal = new CommonGoalType3();
        assertEquals(3, goal.getType());
    }

    @Test
    public void testGetDescription(){
        CommonGoalType3 goal = new CommonGoalType3();
        assertEquals("Four tiles of the same type in the four corners of the bookshelf.", goal.getDescription());
    }

    @Test
    public void testGetCardView(){
        CommonGoalType3 goal = new CommonGoalType3();
        assertEquals("""
                ╔═══════════╗
                ║ ■ - - - ■ ║
                ║ - - - - - ║
                ║ - - - - - ║
                ║ - - - - - ║
                ║ - - - - - ║
                ║ ■ - - - ■ ║
                ╚═══════════╝
                """, goal.getCardView());
    }

    @Test
    public void testToString(){
        CommonGoalType3 goal = new CommonGoalType3();
        assertEquals("commonGoalCard-3", goal.toString());
    }

    @Test
    public void testCheckGoalEligibleShelfAndSameCornerType() {
        CommonGoalType3 goal = new CommonGoalType3();
        ObjectCardType type = ObjectCardType.cat;
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(type, "00"));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(type, "10"));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(type, "00"));
        shelf.getGrid().put(new Coordinate(5, 4), new ObjectCard(type, "00"));
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalEligibleShelfAndDifferentCornerTypes() {
        CommonGoalType3 goal = new CommonGoalType3();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.book, "10"));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(5, 4), new ObjectCard(ObjectCardType.plant, "00"));
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalNotEligibleShelf() {
        CommonGoalType3 goal = new CommonGoalType3();
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.game;
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(type, "00"));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(type, "10"));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(type, "00"));
        assertFalse(goal.checkGoal(shelf));
    }



}