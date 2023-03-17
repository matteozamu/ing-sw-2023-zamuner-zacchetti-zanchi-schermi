package it.polimi.ingsw.model;

// Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti.
// Righe diverse possono avere combinazioni diverse di tipi di tessere.

public class CommonCardType4 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
