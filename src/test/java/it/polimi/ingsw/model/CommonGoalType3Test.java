package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalType3Test {

    @Test
    public void testCheckGoalEligibleShelfAndSameCornerType() {
        CommonGoalType3 goal = new CommonGoalType3();
        ObjectCardType type = ObjectCardType.gatto;
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(type, 0));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(type, 1));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(type, 0));
        shelf.getGrid().put(new Coordinate(5, 4), new ObjectCard(type, 0));
        assertTrue(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalEligibleShelfAndDifferentCornerTypes() {
        CommonGoalType3 goal = new CommonGoalType3();
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.gatto, 0));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(ObjectCardType.libro, 1));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.cornice, 0));
        shelf.getGrid().put(new Coordinate(5, 4), new ObjectCard(ObjectCardType.pianta, 0));
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testCheckGoalNotEligibleShelf() {
        CommonGoalType3 goal = new CommonGoalType3();
        Shelf shelf = new Shelf();
        ObjectCardType type = ObjectCardType.gioco;
        shelf.getGrid().put(new Coordinate(0, 0), new ObjectCard(type, 0));
        shelf.getGrid().put(new Coordinate(0, 4), new ObjectCard(type, 1));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(type, 0));
        assertFalse(goal.checkGoal(shelf));
    }



}