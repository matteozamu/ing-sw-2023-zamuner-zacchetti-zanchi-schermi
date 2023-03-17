package it.polimi.ingsw.model;

// Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2.
// Le tessere dei due gruppi devono essere dello stesso tipo.

public class CommonCardType7 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
