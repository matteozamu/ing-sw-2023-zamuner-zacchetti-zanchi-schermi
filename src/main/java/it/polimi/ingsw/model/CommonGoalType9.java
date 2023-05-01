package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

// OK for TESTING

/**
 * Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti.
 * Colonne diverse possono avere combinazioni diverse di tipi di tessere.
 */
public final class CommonGoalType9 extends CommonGoal {

    public int type = 9;

    public int getType() {
        return type;
    }

    /**
     * Returns a string representation of the common goal, describing its requirements and conditions.
     *
     * @return A string representing the common goal.
     */
    @Override
    public String toString() {
        return "Tre colonne formate ciascuna da 6 tessere di uno, due o tre tipi differenti. Colonne diverse possono avere combinazioni diverse di tipi di tessere.";
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType9, the Shelf must have at least 18 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 18;
    }


    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int columnCount = 0;

        for (int col = 0; col < shelf.COLUMNS; col++) {
            Set<ObjectCardType> uniqueTypesInColumn = new HashSet<>();
            boolean completeColumn = true;

            for (int row = 0; row < shelf.ROWS; row++) {
                Coordinate coordinate = new Coordinate(row, col);
                ObjectCard objectCard = shelf.getObjectCard(coordinate);

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
