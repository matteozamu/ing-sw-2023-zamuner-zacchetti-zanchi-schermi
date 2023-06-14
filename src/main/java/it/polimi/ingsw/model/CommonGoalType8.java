package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Due righe formate ciascuna da 5 diversi tipi di tessere.
 */

public final class CommonGoalType8 extends CommonGoal {

    public int type = 8;

    public String description = """
            Two lines each formed by 5 different
            types of tiles. One line can show the
            same or a different combination of the
            other line.""";

    public String cardView = """
                ╔═══════════╗
                ║           ║
                ║ ■ ■ ■ ■ ■ ║
                ║    x2     ║
                ║           ║
                ║           ║
                ╚═══════════╝
                """;

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCardView() {
        return cardView;
    }

    @Override
    public String toString() {
        return "commonGoalCard-8";
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType8, the Shelf must have at least 10 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 10;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int rowCount = 0;

        for (int row = 0; row < Shelf.ROWS; row++) {
            Set<ObjectCardType> uniqueTypesInRow = new HashSet<>();
            boolean completeRow = true;

            for (int col = 0; col < Shelf.COLUMNS; col++) {
                Coordinate coordinate = new Coordinate(row, col);
                ObjectCard objectCard = shelf.getObjectCard(coordinate);

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
