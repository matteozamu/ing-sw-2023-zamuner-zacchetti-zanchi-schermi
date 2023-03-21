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
    for (int row = 0; row < 5; row++){

        for (int column = 0; column < 4; column++){
            Coordinate coord = new Coordinate(row, column);
            ObjectCard card = shelf.getGrid().get(coord);


        }
    }
    }

    public CommonCardType4(List<ObjectCard> cardToCheck) {
        this.cardToCheck = cardToCheck;
    }
}
