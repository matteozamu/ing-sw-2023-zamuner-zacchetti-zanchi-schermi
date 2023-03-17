package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest extends TestCase {
    private final Game g = new Game();


    //verifico la funzione che controlla se il nick è stato preso
    @Test
    public void isUsernameTaken(){
        assertFalse(g.isUsernameTaken("Pino"));
        g.addPlayer("Pino");
        assertTrue(g.isUsernameTaken("Pino"));
        assertFalse(g.isUsernameTaken("Gigi"));
        assertThrows (NullPointerException.class, () -> {
            g.isUsernameTaken(null);
        });    }

    @Test
    public void addPlayer(){

    }

}