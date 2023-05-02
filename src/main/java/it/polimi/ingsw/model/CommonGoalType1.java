package it.polimi.ingsw.model;

import java.util.Map;


/**
 * Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo.
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */

public final class CommonGoalType1 extends CommonGoal {

    public int type = 1;

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String cliView() {
        return """
                O
                O
                x6
                """;

    }

    /**
     * Returns a string representation of the common goal, describing its requirements and conditions.
     *
     * @return A string representing the common goal.
     */
    @Override
    public String toString() {
        return "Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo. Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.";
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

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        int matchedPairs = 0;

        for (Map.Entry<Coordinate, ObjectCard> entry : grid.entrySet()) {
            Coordinate coord = entry.getKey();
            ObjectCard card = entry.getValue();

            if (checkAdjacent(coord, card, grid)) {
                matchedPairs++;
                if (matchedPairs == 6) {
                    return true;
                }
            }
        }
        return false;
    }

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
