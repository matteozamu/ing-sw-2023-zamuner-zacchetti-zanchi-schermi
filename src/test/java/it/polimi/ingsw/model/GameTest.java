package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

class GameTest extends TestCase {

    private Game g;

    @BeforeEach
    public void setUp(){
        g = new Game();
    }

    @Test
    public void testGetRandomAvailablePersonalGoalCardEmptyContainer(){
        assertNull(g.getRandomAvailablePersonalGoalCard());
    }

    @Test
    public void testGetRandomAvailablePersonalGoalCardFullContainer(){
        g.loadPersonalGoaldCards();
        Object o = g.getRandomAvailablePersonalGoalCard();
        assertTrue(o instanceof PersonalGoalCard);
    }

    @Test
    public void testGetRandomAvailableObjectCardEmptyContainer(){
        assertNull(g.getRandomAvailableObjectCard());
    }

    @Test
    public void testGetRandomAvailableObjectCardFullContainer(){
        g.loadObjectCards();
        Object o = g.getRandomAvailableObjectCard();
        assertTrue(o instanceof ObjectCard);
    }

}