package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

// OK for TESTING

/**
 * Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere.
 */

public final class CommonGoalType11 extends CommonGoal {

    public CommonGoalType11() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        Map<ObjectCardType, Integer> typeCount = new HashMap<>();
        Map<Coordinate, ObjectCard> grid = shelf.getGrid();


        for (Coordinate coord : grid.keySet()) {
            ObjectCard objectCard = grid.get(coord);
            ObjectCardType cardType = objectCard.getType();
            typeCount.put(cardType, typeCount.getOrDefault(cardType, 0) + 1);

            if (typeCount.get(cardType) == 8) {
                return true;
            }
        }
        return false;
    }

}
