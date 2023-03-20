package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.management.openmbean.KeyAlreadyExistsException;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardTest extends TestCase {
    private Board b;
    private Coordinate c;
    private ObjectCard oc;

    @BeforeAll
    public void setUp() {
        b = new Board();
        c = new Coordinate(1, 1);
        oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 0);
    }

    @Test
    public void testKeyAlreadyExists() {
        b.createCell(c, oc);
        assertThrows(KeyAlreadyExistsException.class, () -> {
            b.createCell(c, oc);
        });
    }

    @Test
    public void testNullKey() {
        assertThrows(NullPointerException.class, () -> {
            b.createCell(null, oc);
        });
    }

    @Test

    public void testInvalidCard () {
        assertThrows(InvalidParameterException.class, () -> {
            b.createCell(c, null);
        });
    }
}


