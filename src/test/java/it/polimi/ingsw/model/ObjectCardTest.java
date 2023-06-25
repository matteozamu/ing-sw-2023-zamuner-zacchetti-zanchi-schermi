package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectCardTest {

    @Test
    void getType() {
        ObjectCard objectCard = new ObjectCard(ObjectCardType.plant, "00");
        assertEquals(ObjectCardType.plant, objectCard.getType());
    }

    @Test
    void getId() {
        ObjectCard objectCard = new ObjectCard(ObjectCardType.plant, "10");
        assertEquals("10", objectCard.getId());
    }

    @Test
    void testIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            ObjectCard objectCard = new ObjectCard(ObjectCardType.plant, "???");
        });
    }


}