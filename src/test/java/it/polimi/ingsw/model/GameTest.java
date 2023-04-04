package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GameTest extends TestCase {

    private Game g;

    @BeforeEach
    public void setUp() {
        g = new Game();
    }

    @Test
    public void testGetRandomAvailablePersonalGoalCardEmptyContainer() {
        assertNull(g.getRandomAvailablePersonalGoalCard());
    }

    @Test
    public void testGetRandomAvailablePersonalGoalCardFullContainer() {
        g.getPersonalGoalCardsContainer().add(new PersonalGoalCard(new ArrayList<>()));
        Object o = g.getRandomAvailablePersonalGoalCard();
        assertTrue(o instanceof PersonalGoalCard);
    }

    @Test
    public void testGetRandomAvailableObjectCardEmptyContainer() {
        assertNull(g.getRandomAvailableObjectCard());
    }

    @Test
    public void testGetRandomAvailableObjectCardFullContainer() {
        g.getObjectCardContainer().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        Object o = g.getRandomAvailableObjectCard();
        assertTrue(o instanceof ObjectCard);
    }

}