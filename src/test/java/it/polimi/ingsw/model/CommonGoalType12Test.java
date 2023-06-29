package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.model.Shelf.COLUMNS;
import static it.polimi.ingsw.model.Shelf.ROWS;
import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType12Test {

    @Test
    public void testGetType(){
        CommonGoalType12 goal = new CommonGoalType12();
        assertEquals(12, goal.getType());
    }

    @Test
    public void testGetDescription(){
        CommonGoalType12 goal = new CommonGoalType12();
        assertEquals("""
            Five columns of increasing or decreasing
            height. Starting from the first column on
            the left or on the right, each next column
            must be made of exactly one more tile.
            Tiles can be of any type.""", goal.getDescription());
    }

    @Test
    public void testGetCardView(){
        CommonGoalType12 goal = new CommonGoalType12();
        assertEquals("""
                ╔══════════╗
                ║■ - - - - ║
                ║■ ■ - - - ║
                ║■ ■ ■ - - ║
                ║■ ■ ■ ■ - ║
                ║■ ■ ■ ■ ■ ║
                ╚══════════╝
                """, goal.getCardView());
    }

    @Test
    public void testToString(){
        CommonGoalType12 goal = new CommonGoalType12();
        assertEquals("commonGoalCard-12", goal.toString());
    }

    @Test
    public void testCheckGoalShelfNotEligible(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoal(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(4,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(5,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(4,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(5,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));


        assertTrue(goal.checkGoal(shelf));

    }


    @Test
    public void testCheckDescendingStairReturnsTrue(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(4,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(5,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(4,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        assertTrue(goal.checkDescendingStair(shelf));
    }

    @Test
    public void testCheckDescendingStairReturnsFalse(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        assertFalse(goal.checkDescendingStair(shelf));
    }

    @Test
    public void testCheckAscendingStairReturnsTrue(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(4,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(5,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(4,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        assertTrue(goal.checkAscendingStair(shelf));
    }


    @Test
    public void testCheckAscendingStairReturnsFalse(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        assertFalse(goal.checkAscendingStair(shelf));
    }

    @Test
    public void testFullShelf(){
        CommonGoalType12 goal = new CommonGoalType12();
        Shelf shelf = new Shelf();

        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLUMNS; j++){
                shelf.getGrid().put(new Coordinate(i,j), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
            }
        }

        assertTrue(goal.checkGoal(shelf));
    }
}