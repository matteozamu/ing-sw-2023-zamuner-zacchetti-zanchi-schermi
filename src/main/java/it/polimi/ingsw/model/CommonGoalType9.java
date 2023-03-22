package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti.
 * Colonne diverse possono avere combinazioni diverse di tipi di tessere.
 */
public final class CommonGoalType9 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
        int columnCount = 0;

        for (int col = 0; col < shelf.COLUMNS; col++) {
            Set<ObjectCardType> uniqueTypesInColumn = new HashSet<>();
            boolean completeColumn = true;

            for (int row = 0; row < shelf.ROWS; row++) {
                Coordinate coordinate = new Coordinate(col, row);
                ObjectCard objectCard = shelf.getGrid().get(coordinate);

                if (objectCard == null) {
                    completeColumn = false;
                    break;
                } else {
                    uniqueTypesInColumn.add(objectCard.getType());
                }
            }

            int uniqueTypesCount = uniqueTypesInColumn.size();
            if (completeColumn && (uniqueTypesCount >= 1 && uniqueTypesCount <= 3)) {
                columnCount++;
            }
        }

        return columnCount == 3;
    }

}
