package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//OK

/**
 * Classe che rappresenta un obiettivo comune di tipo 3.
 * L'obiettivo è raggiunto quando le quattro tessere ai quattro angoli della Shelf
 * sono dello stesso tipo.
 */

public class CommonGoalType3 extends CommonGoal {

    public CommonGoalType3() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        Set<ObjectCardType> cornerTypes = new HashSet<>();

        ObjectCard topLeft = grid.get(new Coordinate(0, 0));
        ObjectCard topRight = grid.get(new Coordinate(4, 0));
        ObjectCard bottomLeft = grid.get(new Coordinate(0, 5));
        ObjectCard bottomRight = grid.get(new Coordinate(4, 5));

        if (topLeft != null) {
            cornerTypes.add(topLeft.getType());
        }
        if (topRight != null) {
            cornerTypes.add(topRight.getType());
        }
        if (bottomLeft != null) {
            cornerTypes.add(bottomLeft.getType());
        }
        if (bottomRight != null) {
            cornerTypes.add(bottomRight.getType());
        }

        return cornerTypes.size() == 1;
    }
}
