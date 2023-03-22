package it.polimi.ingsw.model;

/**
 * Cinque tessere dello stesso tipo che formano una X.
 */
public final class CommonGoalType10 extends CommonGoal {

    public CommonGoalType10() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
