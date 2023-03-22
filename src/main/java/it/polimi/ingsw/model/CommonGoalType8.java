package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

// OK for TESTING

/**
 * Due righe formate ciascuna da 5 diversi tipi di tessere.
 */

public final class CommonGoalType8 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
        int rowCount = 0;

        for (int row = 0; row < shelf.ROWS; row++) {
            Set<ObjectCardType> uniqueTypesInRow = new HashSet<>();
            boolean completeRow = true;

            for (int col = 0; col < shelf.COLUMNS; col++) {
                Coordinate coordinate = new Coordinate(col, row);
                ObjectCard objectCard = shelf.getGrid().get(coordinate);

                if (objectCard == null) {
                    completeRow = false;
                    break;
                } else {
                    uniqueTypesInRow.add(objectCard.getType());
                }
            }

            if (completeRow && uniqueTypesInRow.size() == 5) {
                rowCount++;
            }
        }

        return rowCount == 2;
    }

}
