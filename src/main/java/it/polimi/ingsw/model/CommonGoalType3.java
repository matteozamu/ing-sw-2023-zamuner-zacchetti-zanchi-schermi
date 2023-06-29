package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The four corners of the Shelf must have four object cards of the same type.
 */

public final class CommonGoalType3 extends CommonGoal {

    public int type = 3;

    public String description = "Four tiles of the same type in the four corners of the bookshelf.";

    public String cardView = """
                ╔═══════════╗
                ║ ■ - - - ■ ║
                ║ - - - - - ║
                ║ - - - - - ║
                ║ - - - - - ║
                ║ - - - - - ║
                ║ ■ - - - ■ ║
                ╚═══════════╝
                """;

    /**
     * Gets the type of the CommonGoal.
     * @return the type of the CommonGoal.
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Gets the description of the CommonGoal.
     * @return the description of the CommonGoal.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the visual representation of the CommonGoal.
     * @return the visual representation of the CommonGoal.
     */
    @Override
    public String getCardView() {
        return cardView;
    }


    /**
     * Gets the String representation of the CommonGoal.
     * @return the String representation of the CommonGoal.
     */
    @Override
    public String toString() {
        return "commonGoalCard-3";
    }

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


    /**
     * Checks if the Shelf has four object cards of the same type in the four corners.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf satisfies the commongoal, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        Set<ObjectCardType> cornerTypes = new HashSet<>();

        ObjectCard bottomLeft = grid.get(new Coordinate(0, 0));
        ObjectCard bottomRight = grid.get(new Coordinate(0, 4));
        ObjectCard topLeft = grid.get(new Coordinate(5, 0));
        ObjectCard topRight = grid.get(new Coordinate(5, 4));

        if (topLeft != null && topRight != null && bottomLeft != null && bottomRight != null) {
            cornerTypes.add(topLeft.getType());
            cornerTypes.add(topRight.getType());
            cornerTypes.add(bottomLeft.getType());
            cornerTypes.add(bottomRight.getType());
        }


        return cornerTypes.size() == 1;
    }
}
