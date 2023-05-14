package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere.
 */

public final class CommonGoalType11 extends CommonGoal {

    public int type = 11;

    public String description = "Eight tiles of the same type. There’s no\n" +
            "restriction about the position of these tiles.";

    public String cardView = """
            ╔═════════╗
            ║  ■   ■  ║
            ║  ■ ■ ■  ║
            ║  ■ ■ ■  ║
            ╚═════════╝
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
        return "CommonGoalType11{" +
                "type=" + type +
                ", description='" + description + '\'' +
                ", cardView='" + cardView + '\'' +
                '}';
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType11, the Shelf must have at least 8 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 8;
    }

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
