package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.KeyAlreadyExistsException;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardTest extends TestCase {
    private Board b;
    private Coordinate c;
    private ObjectCard objectCard;

    @BeforeEach
    public void setUp() {
        b = new Board();
        c = new Coordinate(1, 1);
        objectCard = new ObjectCard(ObjectCardType.randomObjectCardType(), 0);
    }

    @Test
    void testGetGrid() {
        Map<Coordinate, ObjectCard> expected = new HashMap<>();
        assertEquals(expected, b.getGrid());
    }



    @Test
    void testRemoveObjectCard() {
        Coordinate c2 = new Coordinate(2, 2);
        ObjectCard objectCard2 = new ObjectCard(ObjectCardType.randomObjectCardType(), 1);
        b.createCell(c, objectCard);
        b.createCell(c2, objectCard2);
        //da separare in unità??
        ObjectCard removedCard = b.removeObjectCard(c);
        assertEquals(objectCard, removedCard);
        assertFalse(b.getGrid().containsKey(c));
        assertTrue(b.getGrid().containsKey(c2));
    }

    @Test
    void testRemovedCardIsNull() {
        ObjectCard nullCard = b.removeObjectCard(c);
        nullCard = b.removeObjectCard(c);
        assertNull(nullCard);
    }


    @Test
    public void testKeyAlreadyExists() {
        b.createCell(c, objectCard);
        assertThrows(KeyAlreadyExistsException.class, () -> {
            b.createCell(c, objectCard);
        });
    }

    @Test
    public void testNullKey() {
        assertThrows(NullPointerException.class, () -> {
            b.createCell(null, objectCard);
        });
    }

    @Test
    public void testInvalidCard () {
        assertThrows(InvalidParameterException.class, () -> {
            b.createCell(c, null);
        });
    }
}

//manca isEmptyAtDirection
