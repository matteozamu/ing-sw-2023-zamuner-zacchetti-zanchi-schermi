package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Two lines each formed by 5 different types of tiles.
 * One line can show the same or a different combination of the other line.
 */

public final class CommonGoalType8 extends CommonGoal {

    /**
     * The type of the common goal.
     */
    public int type = 8;

    /**
     * The description of the common goal.
     */
    public String description = "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.";

    /**
     * The visual representation of the common goal.
     */
    public String cardView = """
            ╔═══════════╗
            ║           ║
            ║ ■ ■ ■ ■ ■ ║
            ║    x2     ║
            ║           ║
            ║           ║
            ╚═══════════╝
            """;

    /**
     * Gets the type of the CommonGoal.
     * @return the type of the CommonGoal.
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Gets the description of the CommonGoal.
     * @return the description of the CommonGoal.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the visual representation of the CommonGoal.
     * @return the visual representation of the CommonGoal.
     */
    @Override
    public String getCardView() {
        return cardView;
    }

    /**
     * Gets the String representation of the CommonGoal.
     * @return the String representation of the CommonGoal.
     */
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

    /**
     * Checks if the Shelf satisfies the CommonGoal.
     * For CommonGoalType8, the Shelf must have two rows each formed by 5 different types of tiles.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf satisfies the CommonGoal, false otherwise.
     */
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