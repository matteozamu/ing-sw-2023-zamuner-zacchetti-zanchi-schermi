package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShelfTest extends TestCase {
    private Shelf s;

    @BeforeEach
    public void setUp() {
        s = new Shelf();
    }

    @Test
    public void testGetNextAvailableRow() {
        // TODO
    }

    @Test
    public void testAddObjectCard() {
        // TODO
    }

    @Test
    public void testCheckFull() {
        // Assumiamo che la Shelf sia inizialmente vuota.
        assertFalse(s.getFull());

        // Riempire la Shelf con carte oggetto.
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                ObjectCard card = new ObjectCard(ObjectCardType.gatto, 0);
                assertTrue(s.addObjectCard(col, card));
            }
        }

        // Verifica che la Shelf sia piena.
        assertTrue(s.getFull());
    }

}
