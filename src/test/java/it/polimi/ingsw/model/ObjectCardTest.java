package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void getColorBG() {
        ObjectCard objectCard = new ObjectCard(ObjectCardType.plant, "10");
        assertEquals("\u001B[45m", objectCard.getType().getColorBG());
    }

    @Test
    void testIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ObjectCard(ObjectCardType.plant, "");
        });
    }

    @Test
    public void testToString(){
        ObjectCard objectCard = new ObjectCard(ObjectCardType.plant, "10");
        assertEquals("\u001B[35mPlant\u001B[0m", objectCard.toString());
    }

}