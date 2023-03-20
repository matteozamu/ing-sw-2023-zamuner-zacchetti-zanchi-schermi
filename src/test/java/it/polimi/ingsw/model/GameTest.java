package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest extends TestCase {
    //private final Game g = new Game();
    private Game g;

    @BeforeAll
    public void setUp() {
        g = new Game();
    }

    //verifico la funzione che controlla se il nick è stato preso
    @Test
    public void testIsUsernameTaken() {
        assertFalse(g.isUsernameTaken("Pino"));
        g.addPlayer("Pino");
        assertTrue(g.isUsernameTaken("Pino"));
        assertFalse(g.isUsernameTaken("Gigi"));
        assertThrows(NullPointerException.class, () -> {
            g.isUsernameTaken(null);
        });
    }

    @Test
    public void testAddPlayer() {
        // Test adding player with unique name
        String playerName = "Alice";
        assertTrue(g.addPlayer(playerName));

        // Test adding player with duplicate name
        String duplicatePlayerName = "Alice";
        assertFalse(g.addPlayer(duplicatePlayerName));

        // Test adding multiple players up to max capacity
        String player2Name = "Pino";
        String player3Name = "Gigi";
        String player4Name = "Simo";
        assertTrue(g.addPlayer(player2Name));
        assertTrue(g.addPlayer(player3Name));
        assertTrue(g.addPlayer(player4Name));

        // Test adding player after reaching max capacity
        String player5Name = "Fede";
        assertFalse(g.addPlayer(player5Name));
    }

    @Test
    public void testCreatePersonalGoals(){
        //TODO

    }
}
