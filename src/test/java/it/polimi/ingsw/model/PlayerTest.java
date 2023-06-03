package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getName() {
        Player player = new Player("federica", new Shelf(), null);
        assertEquals("federica", player.getName());
    }

    @Test
    void getShelf() {
        Player player = new Player("federica", new Shelf(), null);
        assertNotNull(player.getShelf());
    }

    @Test
    void getCurrentPoints() {
        Player player = new Player("federica", new Shelf(), null);
        assertEquals(0, player.getCurrentPoints());
    }

    @Test
    void getPersonalGoalCard() {
        ArrayList<PersonalGoal> goals = new ArrayList<>();
        goals.add(new PersonalGoal(0,0,ObjectCardType.trophy));
        goals.add(new PersonalGoal(1,2,ObjectCardType.cat));
        PersonalGoalCard pg = new PersonalGoalCard(goals);
        Player player = new Player("federica", new Shelf(), pg);
        assertEquals(player.getPersonalGoalCard(), pg);
    }

    @Test
    void setWinner() {
        Player player = new Player("federica", new Shelf(), null);
        player.setWinner(true);
        assertTrue(player.isWinner());
    }

    @Test
    void isWinner() {
        Player player = new Player("federica", new Shelf(), null);
        assertFalse(player.isWinner());
    }

    @Test
    void setCurrentPoints() {
        Player player = new Player("federica", new Shelf(), null);
        player.setCurrentPoints(5);
        assertEquals(5, player.getCurrentPoints());
    }

}