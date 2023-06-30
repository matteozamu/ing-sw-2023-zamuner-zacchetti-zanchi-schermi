package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Eight tiles of the same type.
 * There’s no restriction about the position of these tiles.
 */

public final class CommonGoalType11 extends CommonGoal {

    /**
     * The type of the common goal.
     */
    public int type = 11;

    /**
     * The description of the common goal.
     */
    public String description = "Eight tiles of the same type. There’s no\n" +
            "restriction about the position of these tiles.";

    /**
     * The visual representation of the common goal.
     */
    public String cardView = """
            ╔═════════╗
            ║  ■   ■  ║
            ║  ■ ■ ■  ║
            ║  ■ ■ ■  ║
            ╚═════════╝
            """;

    /**
     * Gets the type of the CommonGoal.
     *
     * @return the type of the CommonGoal.
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Gets the description of the CommonGoal.
     *
     * @return the description of the CommonGoal.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the visual representation of the CommonGoal.
     *
     * @return the visual representation of the CommonGoal.
     */
    @Override
    public String getCardView() {
        return cardView;
    }

    /**
     * Gets the String representation of the CommonGoal.
     *
     * @return the String representation of the CommonGoal.
     */
    @Override
    public String toString() {
        return "commonGoalCard-11";
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType11, the Shelf must have eight tiles of the same type.
     * There’s no restriction about the position of these tiles.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 8;
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType11, the Shelf must have at least 8 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<ObjectCardType, Integer> typeCount = new HashMap<>();
        Map<Coordinate, ObjectCard> grid = shelf.getGrid();

        for (Coordinate coord : grid.keySet()) {
            ObjectCard objectCard = shelf.getObjectCard(coord);
            ObjectCardType cardType = objectCard.getType();
            typeCount.put(cardType, typeCount.getOrDefault(cardType, 0) + 1);

            if (typeCount.get(cardType) == 8) {
                return true;
            }
        }
        return false;
    }
}