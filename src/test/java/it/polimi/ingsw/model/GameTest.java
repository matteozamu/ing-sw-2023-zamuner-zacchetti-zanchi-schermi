package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GameTest extends TestCase {

    private Game g;
    private PersonalGoalCard pg;
    private Shelf shelf;

    @BeforeEach
    public void setUp() {
        g = new Game();
        this.pg = g.getRandomAvailablePersonalGoalCard();
        this.shelf = new Shelf();
    }

    @Test
    public void testAddPlayerNull() {
        assertFalse(g.addPlayer(null));
    }

//    @Test
//    public void testAddPlayerCurrentPlayer() {
//        Player p = new Player("Rebecca", this.shelf, this.pg);
//        g.addPlayer(p);
//        g.setCurrentPlayer(p);
//
//        assertEquals(g.getPlayers().get(0), g.getCurrentPlayer());
//    }

    @Test
    public void testMaxNumbersOfPlayers() {
        Player p = new Player("Giselle", this.shelf, this.pg);
        g.addPlayer(p);
        p = new Player("Madeleine", this.shelf, this.pg);
        g.addPlayer(p);
        p = new Player("Margot", this.shelf, this.pg);
        g.addPlayer(p);
        p = new Player("Yvonne", this.shelf, this.pg);
        g.addPlayer(p);

        p = new Player("Colette", this.shelf, this.pg);
        assertFalse(g.addPlayer(p));
    }

    @Test
    public void testNextPlayerNoPlayers() {
        assertNull(g.nextPlayer());
    }

    @Test
    public void testNextPlayer() {
        Player p = new Player("Ember", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);

        p = new Player("Addison", this.shelf, this.pg);
        g.addPlayer(p);

        assertEquals(g.getPlayers().get(0), g.getCurrentPlayer());

        p = g.nextPlayer();
        assertEquals(g.getCurrentPlayer(), p);
        assertEquals(g.getPlayers().get(1), p);

        p = g.nextPlayer();
        assertEquals(g.getPlayers().get(0), p);
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