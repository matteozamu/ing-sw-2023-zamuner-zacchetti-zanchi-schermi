package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest extends TestCase {
    private final Game g = new Game();


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
        g.addPlayer("Yvonne");
        assertThrows(IllegalStateException.class, () -> {
            g.addPlayer("Colette");
        });
    }

}