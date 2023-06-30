package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Two columns each formed by 6 different types of object cards.
 */
public final class CommonGoalType6 extends CommonGoal {

    /**
     * The type of the common goal.
     */
    public int type = 6;

    /**
     * The description of the common goal.
     */
    public String description = "Two columns each formed by 6 different types of tiles.";

    /**
     * The visual representation of the common goal.
     */
    public String cardView = """
            ╔═════════════╗
            ║      ■      ║
            ║      ■      ║
            ║      ■      ║
            ║      ■  x2  ║
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
        return "commonGoalCard-6";
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType6, the Shelf must have at least 12 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 12;
    }


    /**
     * Checks if the Shelf satisfies the goal.
     * For CommonGoalType6, the Shelf must have two columns each formed by 6 different types of object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf satisfies the goal, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int columnsWithUniqueObjectCards = 0;

        for (int col = 0; col < Shelf.COLUMNS; col++) {
            Map<ObjectCardType, Integer> uniqueObjectCards = new HashMap<>();

            for (int row = 0; row < Shelf.ROWS; row++) {
                Coordinate coordinate = new Coordinate(row, col);
                ObjectCard objectCard = shelf.getObjectCard(coordinate);

                if (objectCard != null) {
                    ObjectCardType cardType = objectCard.getType();
                    uniqueObjectCards.put(cardType, uniqueObjectCards.getOrDefault(cardType, 0) + 1);
                }
            }

            if (uniqueObjectCards.size() == 6 && uniqueObjectCards.values().stream().allMatch(count -> count == 1)) {
                columnsWithUniqueObjectCards++;

                if (columnsWithUniqueObjectCards == 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
