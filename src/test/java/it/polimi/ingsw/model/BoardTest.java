package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardTest extends TestCase {
    private Board b;
    private Coordinate c;
    private ObjectCard objectCard;

    @BeforeEach
    public void setUp() {
        this.b = new Board();
        this.c = new Coordinate(1, 1);
        this.objectCard = new ObjectCard(ObjectCardType.randomObjectCardType(), 0);
    }

    @Test
    public void testGetGrid() {
        Map<Coordinate, ObjectCard> expected = new HashMap<>();
        assertEquals(expected, this.b.getGrid());
    }

    @Test
    public void testRemovedObjectCardIsNull() {
        this.b.removeObjectCard(this.c);
        ObjectCard nullCard = this.b.removeObjectCard(this.c);
        assertNull(nullCard);
    }

    @Test
    public void testRemoveObjectCard() {
        Coordinate c2 = new Coordinate(2, 2);
        ObjectCard objectCard2 = new ObjectCard(ObjectCardType.randomObjectCardType(), 1);

        this.b.createCell(this.c, this.objectCard);
        this.b.createCell(c2, objectCard2);

        //da separare in unità??
        ObjectCard removedCard = this.b.removeObjectCard(this.c);
        assertEquals(this.objectCard, removedCard);
        assertFalse(this.b.getGrid().containsKey(this.c));
        assertTrue(this.b.getGrid().containsKey(c2));
    }

    @Test
    public void testCreateCellNullKey() {
        assertThrows(NullPointerException.class, () -> {
            b.createCell(null, this.objectCard);
        });
    }

    @Test
    public void testCreateCellInvalidCard () {
        assertThrows(IllegalArgumentException.class, () -> {
            b.createCell(c, null);
        });
    }

    @Test
    public void testCreateCellKeyAlreadyExists() {
        this.b.createCell(this.c, this.objectCard);
        assertFalse(this.b.createCell(this.c, this.objectCard));
    }

//manca isEmptyAtDirection

    @Test
    public void testIsEmptyAtDirectionTrue() {
        this.b.createCell(this.c, this.objectCard);

        // Test UP is empty
        assertTrue(this.b.isEmptyAtDirection(this.c, Board.Direction.UP));

        // Test DOWN is empty
        assertTrue(this.b.isEmptyAtDirection(this.c, Board.Direction.DOWN));

        // Test LEFT is empty
        assertTrue(this.b.isEmptyAtDirection(this.c, Board.Direction.LEFT));

        // Test RIGHT is empty
        assertTrue(this.b.isEmptyAtDirection(this.c, Board.Direction.RIGHT));
    }

    @Test
    public void testIsEmptyAtDirectionFalse() {
        Coordinate c1 = new Coordinate(2, 1);
        Coordinate c2 = new Coordinate(0, 1);
        Coordinate c3 = new Coordinate(1, 0);
        Coordinate c4 = new Coordinate(1, 2);

        this.b.createCell(this.c, this.objectCard);

        this.b.createCell(c1, this.objectCard);
        this.b.createCell(c2, this.objectCard);
        this.b.createCell(c3, this.objectCard);
        this.b.createCell(c4, this.objectCard);

        // Test UP is  not empty
        assertFalse(this.b.isEmptyAtDirection(this.c, Board.Direction.UP));

        // Test DOWN is not empty
        assertFalse(this.b.isEmptyAtDirection(this.c, Board.Direction.DOWN));

        // Test LEFT is not empty
        assertFalse(this.b.isEmptyAtDirection(this.c, Board.Direction.LEFT));

        // Test RIGHT is not empty
        assertFalse(this.b.isEmptyAtDirection(this.c, Board.Direction.RIGHT));
    }

    //TODO: rifare test quando sono sistemate le eccezioni
//    @Test
//    public void testIsEmptyAtDirectionIllegalArgumentException(){
//        assertThrows(IllegalArgumentException.class, () -> {
//            this.b.isEmptyAtDirection(this.c, null);
//        });
//    }





}

