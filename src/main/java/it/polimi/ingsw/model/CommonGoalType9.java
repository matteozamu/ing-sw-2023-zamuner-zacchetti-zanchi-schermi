package it.polimi.ingsw.model;

// Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti.
// Colonne diverse possono avere combinazioni diverse di tipi di tessere.
public class CommonGoalType9 extends CommonGoal {

    public CommonGoalType9() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
