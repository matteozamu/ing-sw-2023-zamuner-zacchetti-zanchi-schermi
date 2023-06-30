package it.polimi.ingsw.model;

import java.util.List;
import java.util.Map;

/**
 * Five tiles of the same type forming an X.
 */
public final class CommonGoalType10 extends CommonGoal {

    /**
     * The type of the common goal.
     */
    public int type = 10;

    /**
     * The description of the common goal.
     */
    public String description = "Five tiles of the same type forming an X.";

    /**
     * The visual representation of the common goal.
     */
    public String cardView = """
            ╔═════════╗
            ║  ■   ■  ║
            ║    ■    ║
            ║  ■   ■  ║
            ╚═════════╝
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
        return "commonGoalCard-10";
    }

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

    /**
     * Checks if the Shelf satisfies the goal.
     * For CommonGoalType10, the Shelf must have five tiles of the same type forming an X.
     * @param shelf The Shelf object to evaluate.
     * @return true if the Shelf satisfies the goal, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf){
        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 3; j++){
                if (grid.get(new Coordinate(i, j)) == null) {
                    return false;
                }

                if(grid.get(new Coordinate(i+1, j+1)) == null ||
                        grid.get(new Coordinate(i+2, j+2)) == null || grid.get(new Coordinate(i, j+2)) == null ||
                        grid.get(new Coordinate(i+2, j)) == null) {
                    return false;
                }

                if (grid.get(new Coordinate(i,j)).getType() == grid.get(new Coordinate(i+1, j+1)).getType() &&
                        grid.get(new Coordinate(i,j)).getType() == grid.get(new Coordinate(i+2, j+2)).getType() &&
                        grid.get(new Coordinate(i,j)).getType() == grid.get(new Coordinate(i, j+2)).getType() &&
                        grid.get(new Coordinate(i,j)).getType() == grid.get(new Coordinate(i+2, j)).getType()
                        ) {
                    return true;
                }
            }
        }
        return false;
    }
}