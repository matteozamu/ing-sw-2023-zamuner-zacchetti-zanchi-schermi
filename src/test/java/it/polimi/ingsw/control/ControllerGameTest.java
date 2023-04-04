package it.polimi.ingsw.control;

import it.polimi.ingsw.model.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerGameTest extends TestCase {
    private ControllerGame cg;
    private PersonalGoalCard pg;
    private Shelf shelf;

    @BeforeEach
    public void setUp() {
        this.cg = new ControllerGame();
        this.cg.getGame().loadPersonalGoaldCards();
        this.cg.getGame().loadObjectCards();

        this.pg = cg.getGame().getRandomAvailablePersonalGoalCard();
        this.shelf = new Shelf();
    }

    @Test
    public void testUsernameNull() {
        assertThrows(NullPointerException.class, () -> {
            cg.isUsernameAvailable(null);
        });
    }

    @Test
    public void testUsernameAvailable() {
        assertTrue(cg.isUsernameAvailable("Madeleine"));

        Player p = new Player("Madeleine", this.shelf, this.pg);
        cg.addPlayer(p);

        cg.addPlayer(p);

        assertFalse(cg.isUsernameAvailable("Madeleine"));
        assertTrue(cg.isUsernameAvailable("Daphne"));
    }

    @Test
    public void testAddPlayerNull() {
        assertFalse(cg.addPlayer(null));
    }

//    @Test
//    public void testAddPlayerCurrentPlayer() {
//        Player p = new Player("Rebecca", this.shelf, this.pg);
//        cg.addPlayer(p);
//        cg.setCurrentPlayer(p);
//
//        assertEquals(cg.getPlayers().get(0), cg.getCurrentPlayer());
//    }

    @Test
    public void testMaxNumbersOfPlayers() {
        Player p = new Player("Giselle", this.shelf, this.pg);
        cg.addPlayer(p);
        p = new Player("Madeleine", this.shelf, this.pg);
        cg.addPlayer(p);
        p = new Player("Margot", this.shelf, this.pg);
        cg.addPlayer(p);
        p = new Player("Yvonne", this.shelf, this.pg);
        cg.addPlayer(p);

        p = new Player("Colette", this.shelf, this.pg);
        assertFalse(cg.addPlayer(p));
    }

    @Test
    public void testNextPlayerNoPlayers() {
        assertNull(cg.nextPlayer());
    }

    @Test
    public void testNextPlayer() {
        Player p = new Player("Ember", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        p = new Player("Addison", this.shelf, this.pg);
        cg.addPlayer(p);

        assertEquals(cg.getGame().getPlayers().get(0), cg.getCurrentPlayer());

        p = cg.nextPlayer();
        assertEquals(cg.getCurrentPlayer(), p);
        assertEquals(cg.getGame().getPlayers().get(1), p);

        p = cg.nextPlayer();
        assertEquals(cg.getGame().getPlayers().get(0), p);
    }

    @Test
    public void testAddObjectEmptyLimbo() {
        assertFalse(cg.addObjectCards(1));
    }

    @Test
    public void testAddObjectLimboTooBig() {
        Player p = new Player("Kelleigh", this.shelf, this.pg);
        cg.addPlayer(p);

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));

        assertFalse(cg.addObjectCards(0));

    }

    @Test
    public void testAddObjectCardsNoSpaceShelfColumn() {
        Player p = new Player("Estela", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        for (int i = 0; i < 6; i++) {
            cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        }

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        assertFalse(cg.addObjectCards(0));
    }

    @Test
    public void testAddObjectCards() {
        Player p = new Player("Laia", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        for (int i = 0; i < 4; i++) {
            cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        }

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));

        assertTrue(cg.addObjectCards(0));
    }

    @Test
    public void testAddObjectCardsCardRightPositionShelf() {
        Player p = new Player("Ines", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        ObjectCard oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 2);
        cg.getLimbo().add(oc);

        cg.addObjectCards(0);
        assertEquals(cg.getCurrentPlayer().getShelf().getGrid().get(new Coordinate(0, 0)), oc);
    }

    @Test
    public void testAddObjectCardsShelfIsFull() {
        Player p = new Player("Juanita", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, j), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
            }
        }
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(4, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));

        cg.addObjectCards(5);
        assertTrue(cg.getCurrentPlayer().getShelf().isFull());
    }


    @Test
    public void testIsObjectCardAvailableAllEmptyDirections() {
        Coordinate c = new Coordinate(0, 0);
        assertTrue(cg.isObjectCardAvailable(c));
    }

    @Test
    public void testIsObjectCardAvailableOneDirectionFull() {
        Coordinate c = new Coordinate(0, 4);
        cg.fillBoard();
        assertTrue(cg.isObjectCardAvailable(c));
    }

    @Test
    public void testIsObjectCardAvailableAllDirectiosnFull() {
        Player p = new Player("Becky", this.shelf, this.pg);
        cg.addPlayer(p);
        Coordinate c = new Coordinate(0, 0);

        cg.fillBoard();
        assertFalse(cg.isObjectCardAvailable(c));
    }

    @Test
    public void testAddObjectCardToLimbo() {
        assertTrue(cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1)));
    }

    @Test
    public void testAddObjectCardToLimboSizeLimit() {
        cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        assertFalse(cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1)));
    }

    @Test
    public void testAddObjectCardToLimboNullCard() {
        assertThrows(NullPointerException.class, () -> {
            cg.addObjectCardToLimbo(null);
        });
    }

    @Test
    public void testPointsCalculatorNoCompletedRows() {
        Player p = new Player("Alice", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        assertEquals(0, cg.pointsCalculator());
    }

    @Test
    public void testPointsCalculatorOneCompletedRowInShelf() {
        Player p = new Player("Wejdene", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        ObjectCardType type = ObjectCardType.randomObjectCardType();
        ObjectCard oc = new ObjectCard(type, 0);

        for (int i = 0; i < 5; i++) cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), oc);

        assertEquals(5, cg.pointsCalculator());
    }

    @Test
    public void testPointsCalculatorPersonalGoalCard() {
        Player p = new Player("Karol", this.shelf, this.pg);
        cg.addPlayer(p);
        cg.setCurrentPlayer(p);

        cg.getCurrentPlayer().getPersonalGoalCard().setTargetsReached(4);
        assertEquals(6, cg.pointsCalculator());
    }

    //TODO: pointsCalculator su checkCommonGoal
}