package it.polimi.ingsw.model;

// Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti.
// Righe diverse possono avere combinazioni diverse di tipi di tessere.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonCardType4 extends CommonGoal {
    Map<ObjectCardType, Integer> typeCount = new HashMap<>();
    @Override
    public boolean checkGoal(Shelf shelf) {
    for (int i = 0; i < 5; i++){
        for (int j = 0; j < 4; j++){
            Coordinate coord = new Coordinate(i, j);
            ObjectCard card = shelf.getGrid().get(coord);


        }
    }
    }

    public CommonCardType4(List<ObjectCard> cardToCheck) {
        this.cardToCheck = cardToCheck;
    }
}
