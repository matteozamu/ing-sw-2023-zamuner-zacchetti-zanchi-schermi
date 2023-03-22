package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso tipo.
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */

public class CommonGoalType5 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
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
                coord.getUp(),
                coord.getDown(),
                coord.getLeft(),
                coord.getRight()
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
