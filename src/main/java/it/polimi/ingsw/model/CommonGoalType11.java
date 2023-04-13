package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere.
 */

public final class CommonGoalType11 extends CommonGoal {

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType11, the Shelf must have at least 8 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 8;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

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
