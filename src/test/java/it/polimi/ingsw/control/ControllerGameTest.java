package it.polimi.ingsw.control;

import it.polimi.ingsw.model.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerGameTest extends TestCase {
    private ControllerGame cg;

    @BeforeEach
    public void setUp() {
        cg = new ControllerGame();
    }

    @Test
    public void usernameNull(){
        assertThrows (NullPointerException.class, () -> {
            cg.isUsernameAvailable(null);
        });
    }

    @Test
    public void usernameAvailable(){
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

        assertThrows(IllegalStateException.class, () -> {
            cg.addPlayer("Colette");
        });
    }

   @Test
   public void testDuplicateUsername() {
       cg.addPlayer("Margot");

       assertThrows(IllegalStateException.class, () -> {
           cg.addPlayer("Margot");
       });
    }

    @Test
    public void testNextPlayerNoPlayers() {
        assertThrows(IllegalStateException.class, () -> {
            cg.nextPlayer();
        });
    }
    @Test
    // da dividere in unità ?
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
    public void testAddObjectCardsThrowsArgumentException () {
        assertThrows(IllegalArgumentException.class, () -> {
            cg.addObjectCards(1);
                });
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
        assertThrows(IllegalArgumentException.class, () -> {
            cg.addObjectCards(1);
        });

    }

    @Test
    public void testAddObjectCardsThrowsStateException () {
        cg.addPlayer("Elisa");

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));

        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(4,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );

        assertThrows(IllegalStateException.class, () -> {
            cg.addObjectCards(0);
        });
    }

    @Test
    public void testAddObjectCards () {
        cg.addPlayer("Elisa");

        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));

        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );
        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3,0),new ObjectCard(ObjectCardType.randomObjectCardType(), 1) );

        assertTrue(cg.addObjectCards(0));
    }

    @Test
    public void testAddObjectCardsFillingShelf() {
        cg.addPlayer("Simo");
        ObjectCard oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 2);
        cg.getLimbo().add(oc);
        cg.addObjectCards(0);
        assertEquals(cg.getCurrentPlayer().getShelf().getGrid().get(new Coordinate(0, 0)), oc );
    }

    @Test
    public void testAddObjectCardsCheckIsFull() {
        cg.addPlayer("Fez");
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
        assertTrue(cg.getCurrentPlayer().getShelf().getFull());
    }

    @Test
    public void testAddObjectCardToLimboThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            cg.addObjectCardToLimbo(null);
        });
    }

//    @Test
//    public void testAddObjectCardToLimboThrowsIllegalStateException() {
//        assertThrows(IllegalStateException.class, () -> {
//            cg.addObjectCardToLimbo(new Coordinate(0,0));
//        });
//    }
}