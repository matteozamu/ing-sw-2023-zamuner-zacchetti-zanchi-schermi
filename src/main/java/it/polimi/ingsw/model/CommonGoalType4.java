package it.polimi.ingsw.model;

// Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti.
// Righe diverse possono avere combinazioni diverse di tipi di tessere.

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonGoalType4 extends CommonGoal {

    public CommonGoalType4() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
}
