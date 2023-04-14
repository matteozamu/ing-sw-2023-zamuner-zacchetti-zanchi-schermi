package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// OK for TESTING

/**
 * Classe che rappresenta un obiettivo comune di tipo 3.
 * L'obiettivo è raggiunto quando le quattro tessere ai quattro angoli della Shelf
 * sono dello stesso tipo.
 */

public final class CommonGoalType3 extends CommonGoal {

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType3, the Shelf must have at least 4 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 4;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        Set<ObjectCardType> cornerTypes = new HashSet<>();

        ObjectCard topLeft = grid.get(new Coordinate(0, 0));
        ObjectCard topRight = grid.get(new Coordinate(0, 4));
        ObjectCard bottomLeft = grid.get(new Coordinate(5, 0));
        ObjectCard bottomRight = grid.get(new Coordinate(5, 4));

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
