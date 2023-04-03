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
    //TODO: non passa
    public void testGetRandomAvailablePersonalGoalCardEmptyContainer(){
        assertNull(g.getRandomAvailableObjectCard());
    }

    @Test
    public void testGetRandomAvailablePersonalGoalCardFillingContainer(){
        g.loadPersonalGoaldCards();
        Object o = g.getRandomAvailablePersonalGoalCard();
        assertTrue(o instanceof PersonalGoalCard);
    }


}