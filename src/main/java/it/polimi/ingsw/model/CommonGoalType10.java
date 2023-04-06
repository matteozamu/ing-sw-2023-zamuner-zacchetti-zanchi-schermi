package it.polimi.ingsw.model;

import java.util.List;
import java.util.Map;

// OK for TESTING

/**
 * Cinque tessere dello stesso tipo che formano una X.
 */
public final class CommonGoalType10 extends CommonGoal {

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType10, the Shelf must have at least 5 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 5;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<Coordinate, ObjectCard> grid = shelf.getGrid();

        // Check the center coordinate
        Coordinate centerCoordinate = new Coordinate(2, 2);
        ObjectCard centerObjectCard = grid.get(centerCoordinate);

        if (centerObjectCard == null) {
            return false;
        }

        ObjectCardType centerType = centerObjectCard.getType();

        // Coordinates of the X shape
        List<Coordinate> xShapeCoordinates = List.of(
                new Coordinate(1, 1),
                new Coordinate(1, 3),
                new Coordinate(3, 1),
                new Coordinate(3, 3)
        );

        for (Coordinate coordinate : xShapeCoordinates) {
            ObjectCard objectCard = grid.get(coordinate);
            if (objectCard == null || objectCard.getType() != centerType) {
                return false;
            }
        }

        return true;
    }

}
