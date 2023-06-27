package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CommonGoalType1Test {

    @Test
    public void testToString(){
        CommonGoalType1 commonGoalType1 = new CommonGoalType1();
        assertEquals("commonGoalCard-1", commonGoalType1.toString());
    }

    @Test
    public void testCheckAdjacent() {
        CommonGoalType1 commonGoalType1 = new CommonGoalType1();
        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(0, 1);
        Coordinate c3 = new Coordinate(1, 0);

        Map<Coordinate, ObjectCard> grid = new HashMap<>();
        ObjectCard card1 = new ObjectCard(ObjectCardType.cat, "10");
        ObjectCard card2 = new ObjectCard(ObjectCardType.cat, "20");
        grid.put(c1, card1);
        grid.put(c2, card2);

        // adjacent cards of the same type
        assertTrue(commonGoalType1.checkAdjacent(new Coordinate(0, 0), card1, grid));
        assertTrue(commonGoalType1.checkAdjacent(new Coordinate(0, 1), card2, grid));

        // different type
        ObjectCard card3 = new ObjectCard(ObjectCardType.book, "00");
        grid.put(c3, card3);
        assertFalse(commonGoalType1.checkAdjacent(c3, card3, grid));

        Coordinate c4 = new Coordinate(2,0);
        Coordinate c5 = new Coordinate(2,3);
        ObjectCard card4 = new ObjectCard(ObjectCardType.trophy, "00");
        ObjectCard card5 = new ObjectCard(ObjectCardType.trophy, "00");
        grid.put(c4, card4);
        grid.put(c5, card5);
        assertFalse(commonGoalType1.checkAdjacent(c4, card4, grid));
        assertFalse(commonGoalType1.checkAdjacent(c5, card5, grid));


    }

    @Test
    public void testCheckGoalReturnsTrue(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.book, "10"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(1, 2 ), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1, 3 ), new ObjectCard(ObjectCardType.trophy, "10"));
        shelf.getGrid().put(new Coordinate(2, 0 ), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(2, 1 ), new ObjectCard(ObjectCardType.frame, "10"));
        shelf.getGrid().put(new Coordinate(2, 3 ), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(2, 4 ), new ObjectCard(ObjectCardType.game, "10"));
        CommonGoalType1 goal = new CommonGoalType1();
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalReturnsFalse(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.book, "10"));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(1, 2), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(1, 3), new ObjectCard(ObjectCardType.trophy, "10"));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1, 4), new ObjectCard(ObjectCardType.frame, "10"));
        shelf.getGrid().put(new Coordinate(2, 0), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.game, "10"));
        CommonGoalType1 goal = new CommonGoalType1();
        assertFalse(goal.checkGoal(shelf));
    }

}



