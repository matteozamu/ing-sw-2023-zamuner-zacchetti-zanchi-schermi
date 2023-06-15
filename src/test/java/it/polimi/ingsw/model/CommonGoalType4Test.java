package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CommonGoalType4Test {

    @Test
    public void testCheckGoalNotEligibleShelf() {
        Shelf shelf = new Shelf();
        assertFalse(new CommonGoalType4().checkGoal(shelf));
    }

    @Test
    public void testCheckGoalLessThan4RowsReturnsFalse() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.book, "10"));

        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.frame, "10"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.frame, "20"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.frame, "10"));

        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.game, "10"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.trophy, "20"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(2,4), new ObjectCard(ObjectCardType.plant, "10"));

        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(3,4), new ObjectCard(ObjectCardType.book, "10"));

        assertFalse(new CommonGoalType4().checkGoal(shelf));
    }

    @Test
    public void testCheckGoalValidShelfReturnsTrue() {
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0,0), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(0,1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(0,2), new ObjectCard(ObjectCardType.book, "00"));
        shelf.getGrid().put(new Coordinate(0,3), new ObjectCard(ObjectCardType.cat, "20"));
        shelf.getGrid().put(new Coordinate(0,4), new ObjectCard(ObjectCardType.book, "10"));

        shelf.getGrid().put(new Coordinate(1,0), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1,1), new ObjectCard(ObjectCardType.frame, "10"));
        shelf.getGrid().put(new Coordinate(1,2), new ObjectCard(ObjectCardType.frame, "20"));
        shelf.getGrid().put(new Coordinate(1,3), new ObjectCard(ObjectCardType.frame, "00"));
        shelf.getGrid().put(new Coordinate(1,4), new ObjectCard(ObjectCardType.frame, "10"));

        shelf.getGrid().put(new Coordinate(2,0), new ObjectCard(ObjectCardType.game, "00"));
        shelf.getGrid().put(new Coordinate(2,1), new ObjectCard(ObjectCardType.game, "10"));
        shelf.getGrid().put(new Coordinate(2,2), new ObjectCard(ObjectCardType.trophy, "20"));
        shelf.getGrid().put(new Coordinate(2,3), new ObjectCard(ObjectCardType.trophy, "00"));
        shelf.getGrid().put(new Coordinate(2,4), new ObjectCard(ObjectCardType.plant, "10"));

        shelf.getGrid().put(new Coordinate(3,0), new ObjectCard(ObjectCardType.plant, "00"));
        shelf.getGrid().put(new Coordinate(3,1), new ObjectCard(ObjectCardType.cat, "10"));
        shelf.getGrid().put(new Coordinate(3,2), new ObjectCard(ObjectCardType.cat, "00"));
        shelf.getGrid().put(new Coordinate(3,3), new ObjectCard(ObjectCardType.plant, "20"));
        shelf.getGrid().put(new Coordinate(3,4), new ObjectCard(ObjectCardType.book, "10"));

        assertTrue(new CommonGoalType4().checkGoal(shelf));
    }
}


