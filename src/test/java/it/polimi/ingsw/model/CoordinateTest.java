package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoordinateTest extends TestCase {
    private Coordinate coord, c, c1;

    @BeforeAll
    public void setUp() {
        coord = new Coordinate(2,1);
        c = new Coordinate(3,4);
        c1 = new Coordinate(2,1);
    }

    @Test
    public void testGetColumn() {
        int column = coord.getColumn();
        assertEquals(1, column);
    }

    @Test
    public void testGetRow() {
        int row = coord.getRow();
        assertEquals(2, row);
    }

    @Test
    public void testGetUp() {
        Coordinate c = coord.getAdjacent(Coordinate.Direction.UP);
        assertEquals(c.getRow(), 3);
    }

    @Test
    public void testGetRight() {
        Coordinate c = coord.getAdjacent(Coordinate.Direction.RIGHT);
        assertEquals(c.getColumn(), 2);
    }

    @Test
    public void testGetDown() {
        Coordinate c = coord.getAdjacent(Coordinate.Direction.DOWN);
        assertEquals(c.getRow(), 1);
    }

    @Test
    public void testGetLeft() {
        Coordinate c = coord.getAdjacent(Coordinate.Direction.LEFT);
        assertEquals(c.getColumn(), 0);
    }

    @Test
    public void testEquals() {
        assertTrue(coord.equals(c1));
        assertFalse(coord.equals(c));
    }

    @Test
    public void testToString() {
        coord.toString();
        assertEquals("[2,1]", coord.toString());
    }
}