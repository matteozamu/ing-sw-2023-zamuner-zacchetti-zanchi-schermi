package it.polimi.ingsw.control;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerGameTest extends TestCase {
    private ControllerGame cg;

    @BeforeAll
    public void setUp() {
        cg = new ControllerGame();
    }

    @Test
    public void usernameNull(){
        cg = new ControllerGame();
        assertThrows (NullPointerException.class, () -> {
            cg.isUsernameAvailable(null);
        });
    }

    @Test
    public void usernameAvailable(){
        cg = new ControllerGame();
        assertTrue(cg.isUsernameAvailable("Pino"));
        cg.addPlayer("Pino");
        assertFalse(cg.isUsernameAvailable("Pino"));
        assertTrue(cg.isUsernameAvailable("Gigi"));
    }

    @Test
    public void testAddPlayerNull(){
        cg = new ControllerGame();
        assertThrows(NullPointerException.class, () -> {
            cg.addPlayer(null);
        });
    }
    @Test
    public void testMaxNumbersOfPlayers(){
        cg = new ControllerGame();
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
       cg = new ControllerGame();
       cg.addPlayer("Margot");
       assertThrows(IllegalStateException.class, () -> {
           cg.addPlayer("Margot");
       });
    }
}