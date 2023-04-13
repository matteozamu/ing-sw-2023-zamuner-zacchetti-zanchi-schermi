package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    //TODO: mancano i 2 metodi di load

    @Test
    public void testAddPlayerNull() {
        assertFalse(g.addPlayer(null));
    }

    @Test
    public void testAddPlayerCurrentPlayer() {
        Player p = new Player("Rebecca", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);

        assertEquals(g.getPlayers().get(0), g.getCurrentPlayer());
    }

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


    //TODO: non passa: ritorna true anche se non c'è spazio disponibile nella colonna
    @Test
    public void testAddObjectCardsToShelf() {
        List<ObjectCard> objectCards = new ArrayList<>();
        Player p = new Player("Marcus", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);

        objectCards.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        objectCards.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        objectCards.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));

        assertTrue(g.addObjectCardsToShelf(objectCards, 1));
        assertEquals(3, p.getShelf().getGrid().size());

        List<ObjectCard> objectCards2 = new ArrayList<>();
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));

        assertFalse(g.addObjectCardsToShelf(objectCards2, 1));
        assertEquals(3, p.getShelf().getGrid().size());
    }


    @Test
    public void testAddObjectEmptyLimbo() {
        List<ObjectCard> limbo = new ArrayList<>();
        Player p = new Player("Harry", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);
        assertTrue(g.addObjectCardsToShelf(limbo, 1));
        assertEquals(0, p.getShelf().getGrid().size());
    }

    @Test
    public void testAddObjectCardsCardRightPositionShelf() {
        Player p = new Player("Ines", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);
        List<ObjectCard> limbo = new ArrayList<>();
        ObjectCard oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 2);
        limbo.add(oc);

        g.addObjectCardsToShelf(limbo, 0);
        assertEquals(g.getCurrentPlayer().getShelf().getGrid().get(new Coordinate(0, 0)), oc);
    }

    @Test
    public void testAddObjectCardsShelfIsFull() {
        Player p = new Player("Juanita", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);
        List<ObjectCard> limbo = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, j), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
            }
        }
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(4, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));

        limbo.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));

        g.addObjectCardsToShelf(limbo, 5);
        assertTrue(g.getCurrentPlayer().getShelf().isFull());
    }

    @Test
    void testGetRandomAvailableCommonGoalFullContainer() {
        CommonGoal cg = new CommonGoalType1();
        g.getCommonGoalContainer().add(cg);
        Object o = g.getRandomAvailableCommonGoal();
        assertTrue(o instanceof CommonGoal);
    }

    @Test
    void testGetRandomAvailableCommonGoalEmptyContainer() {
        assertNull(g.getRandomAvailableCommonGoal());
    }



}