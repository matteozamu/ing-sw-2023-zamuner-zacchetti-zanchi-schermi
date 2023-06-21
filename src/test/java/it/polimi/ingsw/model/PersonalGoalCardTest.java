package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalCardTest {

    @Test
    public void testCalxculatePoints() {
        PersonalGoalCard card = new PersonalGoalCard(new ArrayList<>(), "personalGoalCard-1");
        card.setTargetsReached(1);
        assertEquals(1, card.calculatePoints());
        card.setTargetsReached(2);
        assertEquals(2, card.calculatePoints());
        card.setTargetsReached(3);
        assertEquals(4, card.calculatePoints());
        card.setTargetsReached(4);
        assertEquals(6, card.calculatePoints());
        card.setTargetsReached(5);
        assertEquals(9, card.calculatePoints());
        card.setTargetsReached(6);
        assertEquals(12, card.calculatePoints());
    }

    @Test
    public void testGetGoals() {
        PersonalGoalCard card = new PersonalGoalCard(new ArrayList<>(), "personalGoalCard-1");
        assertEquals(0, card.getGoals().size());
    }

    @Test
    public void testSetTargetsReached() {
        PersonalGoalCard card = new PersonalGoalCard(new ArrayList<>(), "personalGoalCard-1");
        card.setTargetsReached(1);
        assertEquals(1, card.calculatePoints());
        card.setTargetsReached(2);
        assertEquals(2, card.calculatePoints());
        card.setTargetsReached(3);
        assertEquals(4, card.calculatePoints());
        card.setTargetsReached(4);
        assertEquals(6, card.calculatePoints());
        card.setTargetsReached(5);
        assertEquals(9, card.calculatePoints());
        card.setTargetsReached(6);
        assertEquals(12, card.calculatePoints());
    }

}