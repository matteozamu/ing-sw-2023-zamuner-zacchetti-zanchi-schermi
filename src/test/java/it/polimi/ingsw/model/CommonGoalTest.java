package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    void getType() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals(1, goal.getType());
    }

    @Test
    void getDescription() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals("""
            Six groups each containing at least
            2 tiles of the same type (not necessarily
            in the depicted shape).
            The tiles of one group can be different
            from those of another group.""", goal.getDescription());
    }

    @Test
    void getCardView() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals("""
            ╔═══════════╗
            ║           ║
            ║     ■     ║
            ║     ■     ║
            ║     x6    ║
            ║           ║
            ╚═══════════╝
            """, goal.getCardView());
    }

    @Test
    void checkGoal() {
        CommonGoal goal = new CommonGoalType1();
        Shelf shelf = new Shelf();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    void isShelfEligible() {
        CommonGoal goal = new CommonGoalType1();
        Shelf shelf = new Shelf();
        assertFalse(goal.isShelfEligible(shelf));
    }

    @Test
    void getCurrentPoints() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals(8, goal.getCurrentPoints());
    }

    @Test
    void updateCurrentPoints() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals(8, goal.updateCurrentPoints(4));
    }
}