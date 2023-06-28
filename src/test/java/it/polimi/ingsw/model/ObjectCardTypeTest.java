package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectCardTypeTest {
    @Test
    public void testGetText(){
        ObjectCardType objectCardType = ObjectCardType.cat;
        assertEquals("Cat", objectCardType.getText());
    }

}