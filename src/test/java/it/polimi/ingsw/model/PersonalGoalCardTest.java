package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonalGoalCardTest {

    @Test
    public void testCalculatePoints() {
        ArrayList<PersonalGoal> goals = new ArrayList<>();
        goals.add(new PersonalGoal(4, 4, ObjectCardType.cat));
        goals.add(new PersonalGoal(0, 2, ObjectCardType.trophy));
        goals.add(new PersonalGoal(2, 3, ObjectCardType.game));
        goals.add(new PersonalGoal(5, 2, ObjectCardType.frame));
        goals.add(new PersonalGoal(3, 3, ObjectCardType.book));
        goals.add(new PersonalGoal(5, 0, ObjectCardType.plant));
        PersonalGoalCard card = new PersonalGoalCard(goals, "personalGoalCard-1");
        Shelf shelf = new Shelf();
        shelf.getGrid().put(new Coordinate(4, 4), new ObjectCard(ObjectCardType.cat, "10"));
        card.setTargetsReached(1);
        assertEquals(1, card.calculatePoints(shelf));
        shelf.getGrid().put(new Coordinate(0, 2), new ObjectCard(ObjectCardType.trophy, "10"));
        card.setTargetsReached(2);
        assertEquals(2, card.calculatePoints(shelf));
        shelf.getGrid().put(new Coordinate(2, 3), new ObjectCard(ObjectCardType.game, "10"));
        card.setTargetsReached(3);
        assertEquals(4, card.calculatePoints(shelf));
        shelf.getGrid().put(new Coordinate(5, 2), new ObjectCard(ObjectCardType.frame, "10"));
        card.setTargetsReached(4);
        assertEquals(6, card.calculatePoints(shelf));
        shelf.getGrid().put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.book, "10"));
        card.setTargetsReached(5);
        assertEquals(9, card.calculatePoints(shelf));
        shelf.getGrid().put(new Coordinate(5, 0), new ObjectCard(ObjectCardType.plant, "10"));
        card.setTargetsReached(6);
        assertEquals(12, card.calculatePoints(shelf));
    }

    @Test
    public void testGetGoals() {
        PersonalGoalCard card = new PersonalGoalCard(new ArrayList<>(), "personalGoalCard-1");
        assertEquals(0, card.getGoals().size());
    }

    @Test
    public void testGetID() {
        PersonalGoalCard card = new PersonalGoalCard(new ArrayList<>(), "personalGoalCard-1");
        assertEquals("personalGoalCard-1", card.getID());
    }

}