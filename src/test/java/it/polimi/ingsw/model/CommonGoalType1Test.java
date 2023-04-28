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
        ObjectCard card1 = new ObjectCard(ObjectCardType.gatto, 1);
        ObjectCard card2 = new ObjectCard(ObjectCardType.gatto, 2);
        grid.put(c1, card1);
        grid.put(c2, card2);

        // adjacent cards of the same type
        assertTrue(commonGoalType1.checkAdjacent(new Coordinate(0, 0), card1, grid));
        assertTrue(commonGoalType1.checkAdjacent(new Coordinate(0, 1), card2, grid));

        // different type
        ObjectCard card3 = new ObjectCard(ObjectCardType.libro, 0);
        grid.put(c3, card3);
        assertFalse(commonGoalType1.checkAdjacent(c3, card3, grid));
    }

    @Test
    public void testCheckGoal(){
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.gatto, 1));
        shelf.getGrid().put(new Coordinate(0, 1), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.libro, 1));
        shelf.getGrid().put(new Coordinate(0, 3), new ObjectCard(ObjectCardType.libro, 0));
        shelf.getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.pianta, 0));
        shelf.getGrid().put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.pianta, 2));
        shelf.getGrid().put(new Coordinate(1, 2 ), new ObjectCard(ObjectCardType.trofeo, 0));
        shelf.getGrid().put(new Coordinate(1, 3 ), new ObjectCard(ObjectCardType.trofeo, 1));
        shelf.getGrid().put(new Coordinate(2, 0 ), new ObjectCard(ObjectCardType.cornice, 0));
        shelf.getGrid().put(new Coordinate(2, 1 ), new ObjectCard(ObjectCardType.cornice, 1));
        shelf.getGrid().put(new Coordinate(2, 3 ), new ObjectCard(ObjectCardType.gioco, 0));
        shelf.getGrid().put(new Coordinate(2, 4 ), new ObjectCard(ObjectCardType.gioco, 1));
        CommonGoalType1 goal = new CommonGoalType1();
        assertTrue(goal.checkGoal(shelf));
    }


}



