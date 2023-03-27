package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// TODO : non manca il test del metodo addObjectCard(int col, ObjectCard card)?

public class ShelfTest extends TestCase {
    private Shelf s;
    private ObjectCard oc;

    @BeforeEach
    public void setUp() {
        s = new Shelf();
        oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 1);
    }

    @Test
    void testGetAvailableRowsReturnsCorrectRow() {
        s.addObjectCard(0, oc);
        int result = s.getAvailableRows(0);
        assertEquals(4, result);
    }

    @Test
    void testGetAvailableRowsThrowsIllegalStateExceptionWhenColumnIsFull() {
        for (int i = 0; i < s.ROWS; i++) {
            s.addObjectCard(0, oc);
        }
        assertThrows(IllegalStateException.class, () -> s.getAvailableRows(0));
    }

    @Test
    public void testCheckFull() {
        // Assumiamo che la Shelf sia inizialmente vuota.
        assertFalse(s.checkFull());
        // Riempire la Shelf con carte oggetto.
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                ObjectCard card = new ObjectCard(ObjectCardType.gatto, 0);
                assertTrue(s.addObjectCard(col, card));
            }
        }
        // Verifica che la Shelf sia piena.
        assertTrue(s.checkFull());
    }
}
