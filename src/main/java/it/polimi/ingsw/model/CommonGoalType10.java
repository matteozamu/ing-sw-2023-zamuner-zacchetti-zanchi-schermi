package it.polimi.ingsw.model;

import java.util.List;
import java.util.Map;

/**
 * Cinque tessere dello stesso tipo che formano una X.
 */
public final class CommonGoalType10 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
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
