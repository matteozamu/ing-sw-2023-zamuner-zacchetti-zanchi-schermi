package it.polimi.ingsw.model;

import it.polimi.ingsw.utility.JsonReader;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class GameTest extends TestCase {
    private Game g;
    private PersonalGoalCard pg;
    private Shelf shelf;

    @BeforeEach
    public void setUp() {
        JsonReader.readJsonConstant("GameConstant.json");
        g = new Game();
        this.pg = g.getRandomAvailablePersonalGoalCard();
        this.shelf = new Shelf();

    }

    @Test
    public void testGetGameName(){
        assertEquals("GenericGame", g.getGameName());
    }

    @Test
    public void testSetGameName(){
        g.setGameName("NewName");
        assertEquals("NewName", g.getGameName());
    }

    @Test
    public void testIsStarted(){
        assertFalse(g.isStarted());
    }

    @Test
    public void testSetStarted(){
        g.setStarted(true);
        assertTrue(g.isStarted());
    }

    @Test
    public void testGetInstanceMap() {
        Map<String, Game> map = Game.getInstanceMap();
        assertNotNull(map);
    }

    @Test
    public void testGetInstance() {
        Game g = Game.getInstance("test");
        assertNotNull(g);
    }

    @Test
    public void testGetNumberOfPlayers() {
        assertEquals(-1, g.getNumberOfPlayers());
    }

    @Test
    public void testSetNumberOfPlayers() {
        g.setNumberOfPlayers(2);
        assertEquals(2, g.getNumberOfPlayers());
    }

    @Test
    public void testGetLimbo() {
        assertNotNull(g.getLimbo());
    }

    @Test
    public void testSetLimbo() {
        g.setLimbo(new LinkedHashMap<>());
        assertNotNull(g.getLimbo());
    }

    @Test
    public void testGetLimboOrder() {
        g.setLimboOrder(new ArrayList<>());
        assertNotNull(g.getLimboOrder());
    }

    @Test
    public void testSetLimboOrder() {
        g.setLimboOrder(new ArrayList<>());
        assertNotNull(g.getLimboOrder());
    }

    @Test
    public void testGetCommonGoalContainer() {
        assertNotNull(g.getCommonGoalContainer());
    }

    @Test
    public void testGetPersonalGoalCardsContainer() {
        assertNotNull(g.getPersonalGoalCardsContainer());
    }

    @Test
    public void testSetPersonalGoalCardsContainer() {
        g.setPersonalGoalCardsContainer(new ArrayList<>());
        assertNotNull(g.getPersonalGoalCardsContainer());
    }

    @Test
    public void testGetObjectCardContainer() {
        assertNotNull(g.getObjectCardContainer());
    }

    @Test
    public void testGetPlayers() {
        assertNotNull(g.getPlayers());
    }

    @Test
    public void testAddPlayer() {
        Player p = new Player("Matteo", this.shelf, this.pg);
        assertTrue(g.addPlayer(p));
    }

    @Test
    public void TestGetCurrentPlayer() {
        assertNull(g.getCurrentPlayer());
    }

    @Test
    public void TestSetCurrentPlayer() {
        Player p = new Player("Matteo", this.shelf, this.pg);
        g.setCurrentPlayer(p);
        assertEquals(p, g.getCurrentPlayer());
    }

    @Test
    public void testGetBoard() {
        assertNotNull(g.getBoard());
    }

    @Test
    public void testGetCommonGoals() {
        assertNotNull(g.getCommonGoals());
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
    public void testNextPlayerSinglePlayer() {
        Player p = new Player("Maddy", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);
        assertEquals(p, g.nextPlayer());

        Player p2 = new Player("Patty", this.shelf, this.pg);
        g.addPlayer(p2);
        p2.setConnected(false);

        Player p3 = g.nextPlayer();
        assertEquals(p3, p);
    }

    @Test
    public void testGetPlayersNames() {
        Player p = new Player("Ember", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);

        p = new Player("Addison", this.shelf, this.pg);
        g.addPlayer(p);

        ArrayList<String> names = new ArrayList<>();
        names.add("Ember");
        names.add("Addison");

        assertEquals(names, g.getPlayersNames());
    }

    @Test
    public void testGetPlayerByName() {
        Player p = new Player("Ember", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);

        p = new Player("Addison", this.shelf, this.pg);
        g.addPlayer(p);

        assertEquals(g.getPlayers().get(0), g.getPlayerByName("Ember"));
        assertEquals(g.getPlayers().get(1), g.getPlayerByName("Addison"));
        assertNull(g.getPlayerByName("test"));
    }


    @Test
    public void testGetRandomAvailablePersonalGoalCardFullContainer() {
        g.getPersonalGoalCardsContainer().add(new PersonalGoalCard(new ArrayList<>(), "personalGoalCard-1"));
        Object o = g.getRandomAvailablePersonalGoalCard();
        assertTrue(o instanceof PersonalGoalCard);
    }


    @Test
    public void testGetRandomAvailableObjectCardFullContainer() {
        g.getObjectCardContainer().add(new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        Object o = g.getRandomAvailableObjectCard();
        assertTrue(o instanceof ObjectCard);
    }

    @Test
    public void testAddObjectCardsToShelf() {
        List<ObjectCard> objectCards = new ArrayList<>();
        Player p = new Player("Marcus", this.shelf, this.pg);
        g.addPlayer(p);
        g.setCurrentPlayer(p);

        objectCards.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
        objectCards.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "20"));
        objectCards.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        assertTrue(g.addObjectCardsToShelf(objectCards, 1));
        assertEquals(3, p.getShelf().getGrid().size());

        List<ObjectCard> objectCards2 = new ArrayList<>();
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "20"));
        objectCards2.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

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
        ObjectCard oc = new ObjectCard(ObjectCardType.randomObjectCardType(), "20");
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
                g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, j), new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
            }
        }
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
        g.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(4, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));

        limbo.add(new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));

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


}