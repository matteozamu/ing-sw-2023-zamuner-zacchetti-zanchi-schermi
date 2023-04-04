package it.polimi.ingsw.control;

import it.polimi.ingsw.model.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        cg.addPlayer("Madeleine", this.shelf, this.pg);

        assertFalse(cg.isUsernameAvailable("Madeleine"));
        assertTrue(cg.isUsernameAvailable("Daphne"));
    }

    @Test
    public void testAddPlayerNull() {
        assertFalse(cg.addPlayer(null, null, null));
    }

    @Test
    public void testAddPlayerCurrentPlayer() {
        cg.addPlayer("Rebecca", this.shelf, this.pg);
        assertEquals(cg.getPlayers().get(0), cg.getCurrentPlayer());
    }

    @Test
    public void testAddPlayerAttributesNullShelf() {
        assertFalse(cg.addPlayer("Ayra", null, this.pg));
    }

    @Test
    public void testAddPlayerAttributesNullPersonalGoalCard() {
        assertFalse(cg.addPlayer("Ayra", this.shelf, null));
    }

    @Test
    public void testMaxNumbersOfPlayers() {
        cg.addPlayer("Giselle", this.shelf, this.pg);
        cg.addPlayer("Madeleine", this.shelf, this.pg);
        cg.addPlayer("Margot", this.shelf, this.pg);
        cg.addPlayer("Yvonne", this.shelf, this.pg);

        assertFalse(cg.addPlayer("Colette", this.shelf, this.pg));
    }

    @Test
    public void testNextPlayerNoPlayers() {
        assertNull(cg.nextPlayer());
    }

    @Test
    public void testNextPlayer() {
        cg.addPlayer("Ember", this.shelf, this.pg);
        cg.addPlayer("Addison", this.shelf, this.pg);

        assertEquals(cg.getPlayers().get(0), cg.getCurrentPlayer());

        Player p = cg.nextPlayer();
        assertEquals(cg.getCurrentPlayer(), p);
        assertEquals(cg.getPlayers().get(1), p);

        p = cg.nextPlayer();
        assertEquals(cg.getPlayers().get(0), p);
    }

    @Test
    public void testAddObjectEmptyLimbo() {
        assertFalse(cg.addObjectCards(1));
    }

    @Test
    public void testAddObjectLimboTooBig() {
        cg.addPlayer("Kelleigh", this.shelf, this.pg);

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));

        assertFalse(cg.addObjectCards(0));

    }

    @Test
    public void testAddObjectCardsNoSpaceShelfColumn() {
        cg.addPlayer("Estela", this.shelf, this.pg);

        for (int i = 0; i < 6; i++) {
            cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        }

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        assertFalse(cg.addObjectCards(0));
    }

    @Test
    public void testAddObjectCards() {
        cg.addPlayer("Laia", this.shelf, this.pg);

        for (int i = 0; i < 4; i++) {
            cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        }

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));

        assertTrue(cg.addObjectCards(0));
    }

    @Test
    public void testAddObjectCardsCardRightPositionShelf() {
        cg.addPlayer("Ines", this.shelf, this.pg);
        ObjectCard oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 2);
        cg.getLimbo().add(oc);

        cg.addObjectCards(0);
        assertEquals(cg.getCurrentPlayer().getShelf().getGrid().get(new Coordinate(0, 0)), oc);
    }

    @Test
    public void testAddObjectCardsShelfIsFull() {
        cg.addPlayer("Juanita", this.shelf, this.pg);
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
        Coordinate c = new Coordinate(0, 0);
        cg.addPlayer("Rebecca", this.shelf, this.pg);
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
        cg.addPlayer("Alice", this.shelf, this.pg);
        assertEquals(0, cg.pointsCalculator());
    }

    @Test
    public void testPointsCalculatorOneCompletedRowInShelf() {
        cg.addPlayer("Wejdene", this.shelf, this.pg);

        ObjectCardType type = ObjectCardType.randomObjectCardType();
        ObjectCard oc = new ObjectCard(type, 0);

        for (int i = 0; i < 5; i++) cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), oc);

        assertEquals(5, cg.pointsCalculator());
    }

    @Test
    public void testPointsCalculatorPersonalGoalCard() {
        cg.addPlayer("Karol", this.shelf, this.pg);
        cg.getCurrentPlayer().getPersonalGoalCard().setTargetsReached(4);
        assertEquals(6, cg.pointsCalculator());
    }

    //TODO: pointsCalculator su checkCommonGoal
}