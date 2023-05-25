package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CommonGoalType1Test {

    @Test
    public void testCheckAdjacent() {
        CommonGoalType1 commonGoalType1 = new CommonGoalType1();
        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(0, 1);
        Coordinate c3 = new Coordinate(1, 0);

        Map<Coordinate, ObjectCard> grid = new HashMap<>();
        ObjectCard card1 = new ObjectCard(ObjectCardType.cat, 1);
        ObjectCard card2 = new ObjectCard(ObjectCardType.cat, 2);
        grid.put(c1, card1);
        grid.put(c2, card2);

        // adjacent cards of the same type
        assertTrue(commonGoalType1.checkAdjacent(new Coordinate(0, 0), card1, grid));
        assertTrue(commonGoalType1.checkAdjacent(new Coordinate(0, 1), card2, grid));

        // different type
        ObjectCard card3 = new ObjectCard(ObjectCardType.book, 0);
        grid.put(c3, card3);
        assertFalse(commonGoalType1.checkAdjacent(c3, card3, grid));
    }

    @Test
    public void testCheckGoal(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.cat, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.cat, 0));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.book, 1));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.book, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.plant, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.plant, 2));
        shelf.getGrid().put(new Coordinate(1, 2 ), new ObjectCard(ObjectCardType.trophy, 0));
        shelf.getGrid().put(new Coordinate(1, 3 ), new ObjectCard(ObjectCardType.trophy, 1));
        shelf.getGrid().put(new Coordinate(2, 0 ), new ObjectCard(ObjectCardType.frame, 0));
        shelf.getGrid().put(new Coordinate(2, 1 ), new ObjectCard(ObjectCardType.frame, 1));
        shelf.getGrid().put(new Coordinate(2, 3 ), new ObjectCard(ObjectCardType.game, 0));
        shelf.getGrid().put(new Coordinate(2, 4 ), new ObjectCard(ObjectCardType.game, 1));
        CommonGoalType1 goal = new CommonGoalType1();
        assertTrue(goal.checkGoal(shelf));
    }


}



