package it.polimi.ingsw.model;

import java.util.List;

/**
 * Four separate groups each containing at least 4 adjacent tiles of the same type.
 * The tiles of one group can be different from those of another group.
 */

public class CommonGoalType5 extends CommonGoal {

    public int type = 5;

    public String description = """
            Four groups each containing at least
            4 tiles of the same type (not necessarily
            in the depicted shape).
            The tiles of one group can be different
            from those of another group.""";

    public String cardView = """
            ╔═══════════╗
            ║     ■     ║
            ║     ■     ║
            ║     ■     ║
            ║     ■     ║
            ║     x4    ║
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
        return "commonGoalCard-5";
    }

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


    /**
     * Checks if the Shelf satisfies the CommonGoal.
     * For CommonGoalType5, the Shelf must have four groups of four adjacent tiles of the same type.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf satisfies the CommonGoal, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int counter = 0;

        List<Integer> groups = shelf.findAdjacentTilesGroups();

        for (int i : groups) {
            if (i >= 4) {
                counter++;
            }
        }

        return counter >= 4;
    }
}
