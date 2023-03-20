package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameTest extends TestCase {
    private Game g;

    @BeforeAll
    public void setUp() {
        g = new Game();
    }

    @Test
    public void usernameNull(){
        g = new Game();
        assertThrows (NullPointerException.class, () -> {
            g.isUsernameAvailable(null);
        });
    }

    @Test
    public void usernameAvailable(){
        g = new Game();
        assertTrue(g.isUsernameAvailable("Pino"));
        g.addPlayer("Pino");
        assertFalse(g.isUsernameAvailable("Pino"));
        assertTrue(g.isUsernameAvailable("Gigi"));
    }

    @Test
    public void testAddPlayerNull(){
        g = new Game();
        assertThrows(NullPointerException.class, () -> {
            g.addPlayer(null);
        });
    }
    @Test
    public void testMaxNumbersOfPlayers(){
        g = new Game();
        g.addPlayer("Giselle");
        g.addPlayer("Madeleine");
        g.addPlayer("Margot");
        g.addPlayer("Yvonne");
        assertThrows(IllegalStateException.class, () -> {
            g.addPlayer("Colette");
        });
    }

   @Test
   public void testDuplicateUsername() {
       g = new Game();
       g.addPlayer("Margot");
       // Test adding player with duplicate name
       assertThrows(IllegalStateException.class, () -> {
           g.addPlayer("Margot");
       });
    }
}