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

    @BeforeEach
    public void setUp() {
        cg = new ControllerGame();
        cg.getGame().loadPersonalGoaldCards();
        cg.getGame().loadObjectCards();
    }

    @Test
    public void testUsernameNull(){
        assertThrows (NullPointerException.class, () -> {
            cg.isUsernameAvailable(null);
        });
    }

    @Test
    public void testUsernameAvailable(){
        assertTrue(cg.isUsernameAvailable("Pino"));
        cg.addPlayer("Pino");
        assertFalse(cg.isUsernameAvailable("Pino"));
        assertTrue(cg.isUsernameAvailable("Gigi"));
    }

    @Test
    public void testAddPlayerNull(){
        assertThrows(NullPointerException.class, () -> {
            cg.addPlayer(null);
        });
    }
    @Test
    public void testMaxNumbersOfPlayers(){
        cg.addPlayer("Giselle");
        cg.addPlayer("Madeleine");
        cg.addPlayer("Margot");
        cg.addPlayer("Yvonne");

        assertFalse(cg.addPlayer("Colette"));
    }

   @Test
   public void testDuplicateUsername() {
       cg.addPlayer("Margot");

       assertFalse(cg.addPlayer("Margot"));
    }

    @Test
    public void testNextPlayerNoPlayers() {
        assertNull(cg.nextPlayer());
    }
    @Test
    public void testNextPlayer() {
        cg.addPlayer("Ember");
        cg.addPlayer("Addison");

        assertEquals(cg.getPlayers().get(0), cg.getCurrentPlayer());

        Player p = cg.nextPlayer();
        assertEquals(cg.getCurrentPlayer(), p);
        assertEquals(cg.getPlayers().get(1), p);

        p = cg.nextPlayer();
        assertEquals(cg.getPlayers().get(0), p);
    }

    @Test
    public void testAddObjectEmptyLimbo () {
        assertFalse(cg.addObjectCards(1));
    }

    @Test
    public void testAddObjectLimboTooBig () {
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));

        assertFalse(cg.addObjectCards(1));

    }

    @Test
    public void testAddObjectCardsNoSpaceShelfColumn () {
        cg.addPlayer("Estela");

        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(4,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));

        assertFalse(cg.addObjectCards(0));
    }

    @Test
    public void testAddObjectCards () {
        cg.addPlayer("Laia");

        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));

        assertTrue(cg.addObjectCards(0));
    }

    @Test
    public void testAddObjectCardsCardRightPositionShelf() {
        cg.addPlayer("Ines");
        ObjectCard oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 2);
        cg.getLimbo().add(oc);

        cg.addObjectCards(0);
        assertEquals(cg.getCurrentPlayer().getShelf().getGrid().get(new Coordinate(0, 0)), oc );
    }

    @Test
    public void testAddObjectCardsShelfIsFull() {
        cg.addPlayer("Juanita");
        for(int i=0; i< 6; i++) {
            for (int j=0; j<4; j++) {
                cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, j),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
            }
        }
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0, 5),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1, 5),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2, 5),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3, 5),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(4, 5),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));

        cg.addObjectCards(5);
        assertTrue(cg.getCurrentPlayer().getShelf().isFull());
    }


     @Test
     public void testIsObjectCardAvailableAllEmptyDirections(){
        Coordinate c = new Coordinate(0,0);
        assertTrue(cg.isObjectCardAvailable(c));
     }

    @Test
    public void testIsObjectCardAvailableOneDirectionFull(){
        Coordinate c = new Coordinate(0,4);
        cg.addPlayer("Rebecca");
        cg.fillBoard();
//        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        assertTrue(cg.isObjectCardAvailable(c));
    }

    @Test
    public void testIsObjectCardAvailableAllDirectiosnFull(){
        Coordinate c = new Coordinate(0,0);
        cg.addPlayer("Rebecca");
        cg.fillBoard();
        assertFalse(cg.isObjectCardAvailable(c));
    }

//    @Test
//    public void testAddObjectCardToLimboFalse(){
//       testare se il limbo ha 3 carte e se la carta non è disponibile
//    }


    //    @Test
//    public void testAddObjectCardToLimboTrue(){
//        testare se il limbo ha <3 carte e se la carta è disponibile
//    }

    @Test
    public void testAddObjectCardToLimboNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            cg.addObjectCardToLimbo(null);
        });
    }

    @Test
    public void testPointsCalculatorNoCompletedRows() {
        cg.addPlayer("Alice");
        assertEquals(0, cg.pointsCalculator());
    }

    @Test
    public void testPointsCalculatorOneCompletedRowInShelf() {
        cg.addPlayer("Alice");

        ObjectCardType type = ObjectCardType.randomObjectCardType();
        ObjectCard oc = new ObjectCard(type, 0);

        for(int i = 0; i < 5; i++) cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), oc);

        assertEquals(5, cg.pointsCalculator());
    }
    @Test
    public void testPointsCalculatorPersonalGoalCard() {
        cg.addPlayer("Karol");
        cg.getCurrentPlayer().getPersonalGoalCard().setTargetsReached(4);
        assertEquals(6,cg.pointsCalculator());
    }

    //TODO: pointsCalculator su checkCommonGoal







}