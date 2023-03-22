package it.polimi.ingsw.model;

/**
 * Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo.
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */

public final class CommonGoalType1 extends CommonGoal {

    public CommonGoalType1() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
