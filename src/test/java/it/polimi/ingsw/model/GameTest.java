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
    public void usernameAvailable(){
        assertTrue(g.isUsernameAvailable("Pino"));
        g.addPlayer("Pino");
        assertFalse(g.isUsernameAvailable("Pino"));
        assertTrue(g.isUsernameAvailable("Gigi"));
    }

//    @Test
//    public void testAddPlayerNull(){
//        assertThrows(NullPointerException.class, () -> {
//            g.addPlayer(null);
//        });
//    }
    @Test
    public void testMaxNumbersOfPlayers(){
        g.addPlayer("Giselle");
        g.addPlayer("Madeleine");
        g.addPlayer("Margot");
//        g.addPlayer("Yvonne");
        assertThrows(IllegalStateException.class, () -> {
            g.addPlayer("Colette");
        });
    }

//    @Test
//    public void addPlayer() {
//        // Test adding player with unique name
//        String playerName = "Alice";
//        assertTrue(g.addPlayer(playerName));
//
//        // Test adding player with duplicate name
//        String duplicatePlayerName = "Alice";
//        assertFalse(g.addPlayer(duplicatePlayerName));
//
//        // Test adding multiple players up to max capacity
//        String player2Name = "Pino";
//        String player3Name = "Gigi";
//        String player4Name = "Simo";
//        assertTrue(g.addPlayer(player2Name));
//        assertTrue(g.addPlayer(player3Name));
//        assertTrue(g.addPlayer(player4Name));
//
//        // Test adding player after reaching max capacity
//        String player5Name = "Fede";
//        assertFalse(g.addPlayer(player5Name));
//    }
}