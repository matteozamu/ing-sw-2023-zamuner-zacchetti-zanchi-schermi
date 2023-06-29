package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Three columns each formed by 6 tiles of maximum three different types.
 * One column can show the same or a different combination of another column.
 */
public final class CommonGoalType9 extends CommonGoal {

    public int type = 9;

    public String description = """
            Three columns each formed by 6 tiles
            of maximum three different types. One
            column can show the same or a different
            combination of another column.""";

    public String cardView = """
            ╔═════════════╗
            ║      ■      ║
            ║      ■      ║
            ║      ■      ║
            ║      ■  x3  ║
            ║      ■      ║
            ║      ■      ║
            ╚═════════════╝
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
        return "commonGoalCard-9";
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


    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType9, the Shelf must have three columns each formed by 6 tiles
     * of maximum three different types. One  column can show the same or a different
     * combination of another column.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int columnCount = 0;

        for (int col = 0; col < Shelf.COLUMNS; col++) {
            Set<ObjectCardType> uniqueTypesInColumn = new HashSet<>();
            boolean completeColumn = true;

            for (int row = 0; row < Shelf.ROWS; row++) {
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
