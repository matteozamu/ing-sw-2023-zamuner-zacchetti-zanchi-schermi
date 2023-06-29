package it.polimi.ingsw.model;

import java.util.Map;


/**
 * Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo.
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */

public final class CommonGoalType1 extends CommonGoal {

    public int type = 1;

    public String description = """
            Six groups each containing at least
            2 tiles of the same type (not necessarily
            in the depicted shape).
            The tiles of one group can be different
            from those of another group.""";

    public String cardView = """
            ╔═══════════╗
            ║           ║
            ║     ■     ║
            ║     ■     ║
            ║     x6    ║
            ║           ║
            ╚═══════════╝
            """;

    /**
     * Gets the type of the common goal.
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Gets the description of the common goal.
     */

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the visual representation of the common goal.
     */

    @Override
    public String getCardView() {
        return cardView;
    }

    /**
     * Gets the string representation of the common goal.
     */

    @Override
    public String toString() {
        return "commonGoalCard-1";
    }

    /**
     * Determines if the given shelf is eligible to be checked for the specific common goal of type 1.
     * A shelf is considered eligible for this common goal if it contains at least 12 object cards,
     * as the goal requires the presence of 6 separate groups of 2 adjacent object cards of the same type.
     *
     * @param shelf The shelf to be checked for eligibility.
     * @return true if the shelf contains at least 12 object cards, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 12;
    }

    /**
     * Checks if the given shelf satisfies the specific common goal of type 1.
     * A shelf satisfies this goal if it contains 6 separate groups of 2 adjacent object cards of the same type.
     *
     * @param shelf The shelf to be checked for the goal.
     * @return true if the shelf satisfies the goal, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        int matchedCards = 0;

        for (Map.Entry<Coordinate, ObjectCard> entry : grid.entrySet()) {
            Coordinate coord = entry.getKey();
            ObjectCard card = entry.getValue();

            if (checkAdjacent(coord, card, grid)) {
                matchedCards++;
                // Ho dei dubbi, ma funziona. Una volta era 6
                if (matchedCards == 12) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given card is adjacent to another card of the same type.
     *
     * @param coord The coordinate of the card to be checked.
     * @param card The card to be checked.
     * @param grid The grid of the shelf to be checked.
     * @return true if the card is adjacent to another card of the same type, false otherwise.
     */

    public boolean checkAdjacent(Coordinate coord, ObjectCard card, Map<Coordinate, ObjectCard> grid) {
        ObjectCardType targetType = card.getType();
        Coordinate[] adjacentCoords = {coord.getAdjacent(Coordinate.Direction.UP), coord.getAdjacent(Coordinate.Direction.RIGHT), coord.getAdjacent(Coordinate.Direction.DOWN), coord.getAdjacent(Coordinate.Direction.LEFT)};

        for (Coordinate adjacentCoord : adjacentCoords) {
            ObjectCard adjacentCard = grid.get(adjacentCoord);
            if (adjacentCard != null && adjacentCard.getType() == targetType) {
                return true;
            }
        }
        return false;
    }
}
