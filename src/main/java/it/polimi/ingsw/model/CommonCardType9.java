package it.polimi.ingsw.model;

// Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti.
// Colonne diverse possono avere combinazioni diverse di tipi di tessere.
public class CommonCardType9 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
