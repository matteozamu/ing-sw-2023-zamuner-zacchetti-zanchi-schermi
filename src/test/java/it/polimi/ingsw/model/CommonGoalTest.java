package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTest {

    @Test
    public void testGetType() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals(1, goal.getType());
    }

    @Test
    public void testGetDescription() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals("""
            Six groups each containing at least
            2 tiles of the same type (not necessarily
            in the depicted shape).
            The tiles of one group can be different
            from those of another group.""", goal.getDescription());
    }

    @Test
    public void testGetCardView() {
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
    public void testCheckGoal() {
        CommonGoal goal = new CommonGoalType1();
        Shelf shelf = new Shelf();
        assertFalse(goal.checkGoal(shelf));
    }

    @Test
    public void testIsShelfEligible() {
        CommonGoal goal = new CommonGoalType1();
        Shelf shelf = new Shelf();
        assertFalse(goal.isShelfEligible(shelf));
    }

    @Test
    public void TestGetCurrentPoints() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals(8, goal.getCurrentPoints());
    }

    @Test
    public void testUpdateCurrentPoints1() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals(8, goal.updateCurrentPoints(4));
    }

    @Test
    public void testUpdateCurrentPoints2() {
        CommonGoal goal = new CommonGoalType1();
        assertEquals(8, goal.updateCurrentPoints(4));
    }



}