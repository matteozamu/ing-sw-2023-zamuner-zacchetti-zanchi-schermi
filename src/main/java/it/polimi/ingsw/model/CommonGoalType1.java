package it.polimi.ingsw.model;

import java.util.Map;

/**
 * Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo.
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */

public final class CommonGoalType1 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
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

    private boolean checkAdjacent(Coordinate coord, ObjectCard card, Map<Coordinate, ObjectCard> grid) {
        ObjectCardType targetType = card.getType();
        Coordinate[] adjacentCoords = {coord.getUp(), coord.getRight(), coord.getDown(), coord.getLeft()};

        for (Coordinate adjacentCoord : adjacentCoords) {
            ObjectCard adjacentCard = grid.get(adjacentCoord);
            if (adjacentCard != null && adjacentCard.getType() == targetType) {
                return true;
            }
        }
        return false;
    }
}
