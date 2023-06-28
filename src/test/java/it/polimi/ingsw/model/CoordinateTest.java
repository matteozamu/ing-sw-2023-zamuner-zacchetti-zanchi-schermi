package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoordinateTest extends TestCase {
    private Coordinate cord, c, c1;

    @BeforeAll
    public void setUp() {
        cord = new Coordinate(2, 1);
        c = new Coordinate(3, 4);
        c1 = new Coordinate(2, 1);
    }

    @Test
    public void testGetColumn() {
        int column = cord.getColumn();
        assertEquals(1, column);
    }

    @Test
    public void testGetRow() {
        int row = cord.getRow();
        assertEquals(2, row);
    }

    @Test
    public void testGetUp() {
        Coordinate c = cord.getAdjacent(Coordinate.Direction.UP);
        assertEquals(c.getRow(), 3);
    }

    @Test
    public void testGetRight() {
        Coordinate c = cord.getAdjacent(Coordinate.Direction.RIGHT);
        assertEquals(c.getColumn(), 2);
    }

    @Test
    public void testGetDown() {
        Coordinate c = cord.getAdjacent(Coordinate.Direction.DOWN);
        assertEquals(c.getRow(), 1);
    }

    @Test
    public void testGetLeft() {
        Coordinate c = cord.getAdjacent(Coordinate.Direction.LEFT);
        assertEquals(c.getColumn(), 0);
    }

    @Test
    public void testEquals() {
        assertTrue(cord.equals(c1));
        assertFalse(cord.equals(c));
    }

    @Test
    public void testToString() {
        cord.toString();
        assertEquals("[2,1]", cord.toString());
    }

    @Test
    public void testEqualsReturnsTrue(){
        Coordinate c = new Coordinate(2,1);
        assertTrue(c.equals(c));
    }
    @Test
    public void testEqualsReturnsFalse(){
        Coordinate c = new Coordinate(2,1);
        assertFalse(c.equals(null));
    }

}