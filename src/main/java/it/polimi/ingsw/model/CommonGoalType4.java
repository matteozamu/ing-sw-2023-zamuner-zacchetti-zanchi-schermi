package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

// OK for TESTING

/**
 * Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti.
 * Righe diverse possono avere combinazioni diverse di tipi di tessere.
 */
public final class CommonGoalType4 extends CommonGoal {

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType4, the Shelf must have at least 20 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 20;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        int validRowCount = 0;

        for (int row = 0; row < shelf.ROWS; row++) {
            Map<ObjectCardType, Integer> rowTypeCount = new HashMap<>();

            for (int col = 0; col < shelf.COLUMNS; col++) {
                Coordinate coordinate = new Coordinate(col, row);
                ObjectCard objectCard = grid.get(coordinate);

                if (objectCard != null) {
                    ObjectCardType cardType = objectCard.getType();
                    rowTypeCount.put(cardType, rowTypeCount.getOrDefault(cardType, 0) + 1);
                }
            }

            if (rowTypeCount.size() >= 1 && rowTypeCount.size() <= 3) {
                int totalCardsInRow = rowTypeCount.values().stream().mapToInt(Integer::intValue).sum();
                if (totalCardsInRow == shelf.COLUMNS) {
                    validRowCount++;
                }
            }
        }

        return validRowCount == 4;
    }

}
