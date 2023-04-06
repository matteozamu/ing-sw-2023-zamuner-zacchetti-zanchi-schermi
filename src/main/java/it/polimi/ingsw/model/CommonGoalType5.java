package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

// OK for TESTING

/**
 * Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso tipo.
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */

public class CommonGoalType5 extends CommonGoal {

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType5, the Shelf must have at least 16 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 16;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<ObjectCardType, Integer> groupsCount = new HashMap<>();

        for (Map.Entry<Coordinate, ObjectCard> entry : shelf.getGrid().entrySet()) {
            Coordinate coordinate = entry.getKey();
            ObjectCard objectCard = entry.getValue();

            if (isValidGroupOfFour(shelf, coordinate, objectCard.getType())) {
                groupsCount.put(objectCard.getType(), groupsCount.getOrDefault(objectCard.getType(), 0) + 1);
            }
        }

        return groupsCount.values().stream().filter(count -> count == 4).count() == 4;
    }


    private boolean isValidGroupOfFour(Shelf shelf, Coordinate coord, ObjectCardType type) {
        Coordinate[] adjacentCoordinates = new Coordinate[]{
                coord.getAdjacent(Coordinate.Direction.UP),
                coord.getAdjacent(Coordinate.Direction.DOWN),
                coord.getAdjacent(Coordinate.Direction.LEFT),
                coord.getAdjacent(Coordinate.Direction.RIGHT)
        };

        int adjacentSameTypeCount = 0;

        for (Coordinate adjacentCoord : adjacentCoordinates) {
            ObjectCard adjacentCard = shelf.getGrid().get(adjacentCoord);
            if (adjacentCard != null && adjacentCard.getType() == type) {
                adjacentSameTypeCount++;
            }
        }

        return adjacentSameTypeCount == 3;
    }

}
