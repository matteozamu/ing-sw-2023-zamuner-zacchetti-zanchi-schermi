package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonalGoalTest {

    @Test
    public void testGetColumn() {
        PersonalGoal personalGoal = new PersonalGoal(0, 1, ObjectCardType.randomObjectCardType());
        assertEquals(1, personalGoal.getColumn());
    }

    @Test
    public void testGetType() {
        PersonalGoal personalGoal = new PersonalGoal(1, 1, ObjectCardType.cat);
        assertEquals(ObjectCardType.cat, personalGoal.getType());
    }

    @Test
    public void testGetRow() {
        PersonalGoal personalGoal = new PersonalGoal(1, 0, ObjectCardType.randomObjectCardType());
        assertEquals(1, personalGoal.getRow());
    }

}