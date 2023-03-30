package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

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
        s.addObjectCards(0, Collections.singletonList(oc));
        int result = s.getAvailableRows(0);
        assertEquals(5, result);
    }

    @Test
    void testGetAvailableRowsThrowsIllegalStateExceptionWhenColumnIsFull() {
        // TODO: da vedere perché il test non passa
        for (int i = 0; i < s.ROWS; i++) {
            s.addObjectCards(0, Collections.singletonList(oc));
        }
        assertThrows(IllegalStateException.class, () -> s.getAvailableRows(0));
    }


    @Test
    public void testCheckFull() {
        // Assumiamo che la Shelf sia inizialmente vuota.
        assertFalse(s.getFull());
        // Riempire la Shelf con carte oggetto.
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                ObjectCard card = new ObjectCard(ObjectCardType.gatto, 0);
                assertTrue(s.addObjectCards(col, Collections.singletonList(card)));
            }
        }
        // Verifica che la Shelf sia piena.
        assertTrue(s.getFull());
    }

}