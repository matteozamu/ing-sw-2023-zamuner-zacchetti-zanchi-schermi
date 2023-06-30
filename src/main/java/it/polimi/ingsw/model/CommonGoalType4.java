package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Four rows each formed by 5 tiles of maximum three different types.
 * One line can show the same or a different combination of another line.
 */
public final class CommonGoalType4 extends CommonGoal {

    /**
     * The type of the common goal.
     */
    public int type = 4;

    /**
     * The description of the common goal.
     */
    public String description = """
            Four lines each formed by 5 tiles of
            maximum three different types. One
            line can show the same or a different
            combination of another line.""";

    /**
     * The visual representation of the common goal.
     */
    public String cardView = """
                ╔═══════════╗
                ║           ║
                ║ ■ ■ ■ ■ ■ ║
                ║    x4     ║
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
        return "commonGoalCard-4";
    }

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


    /**
     * Checks if the Shelf satisfies the goal.
     * For CommonGoalType4, the Shelf must have four lines each formed by 5 tiles of
     * maximum three different types. One line can show the same or a different
     * combination of another line.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf satisfies the goal, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int validRowCount = 0;

        for (int row = 0; row < Shelf.ROWS; row++) {
            Map<ObjectCardType, Integer> rowTypeCount = new HashMap<>();

            for (int col = 0; col < Shelf.COLUMNS; col++) {
                Coordinate coordinate = new Coordinate(row, col);
                ObjectCard objectCard = shelf.getObjectCard(coordinate);

                if (objectCard != null) {
                    ObjectCardType cardType = objectCard.getType();
                    rowTypeCount.put(cardType, rowTypeCount.getOrDefault(cardType, 0) + 1);
                }
            }

            if (rowTypeCount.size() >= 1 && rowTypeCount.size() <= 3) {
                int totalCardsInRow = rowTypeCount.values().stream().mapToInt(Integer::intValue).sum();
                if (totalCardsInRow == Shelf.COLUMNS) {
                    validRowCount++;
                }
            }
        }

        return validRowCount == 4;
    }
}