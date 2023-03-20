package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameTest extends TestCase {
    //private final Game g = new Game();
    private Game g;

    @BeforeAll
    public void setUp() {
        g = new Game();
    }

    @Test
    public void usernameNull(){
        assertThrows (NullPointerException.class, () -> {
            g.isUsernameAvailable(null);
        });
    }

    @Test
    //TODO: verificare come creare test indipendenti tra loro (controllare come "pulire" i test precedenti)
    public void usernameAvailable(){
        assertTrue(g.isUsernameAvailable("Pino"));
     //   g.addPlayer("Pino");
        //  assertFalse(g.isUsernameAvailable("Pino"));
        assertTrue(g.isUsernameAvailable("Gigi"));
    }

    @Test
    public void testAddPlayerNull(){
        assertThrows(NullPointerException.class, () -> {
            g.addPlayer(null);
        });
    }
    @Test
    public void testMaxNumbersOfPlayers(){
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
        //TODO: fix
         g.addPlayer("Margot");
        // Test adding player with duplicate name
        //assertFalse(g.addPlayer(duplicatePlayerName));
       assertThrows(IllegalStateException.class, () -> {
           g.addPlayer("Margot");
       });
    }
}