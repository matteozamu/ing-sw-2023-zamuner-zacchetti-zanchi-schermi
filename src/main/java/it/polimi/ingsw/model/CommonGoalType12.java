package it.polimi.ingsw.model;

/**
 *
 * Five columns of increasing or decreasingcheight.
 * Starting from the first column on the left or on the right,
 * each next column must be made of exactly one more tile.
 * Tiles can be of any type.
 */

public final class CommonGoalType12 extends CommonGoal {

    public int type = 12;

    public String description = """
            Five columns of increasing or decreasing
            height. Starting from the first column on
            the left or on the right, each next column
            must be made of exactly one more tile.
            Tiles can be of any type.""";

    public String cardView = """
                ╔══════════╗
                ║■ - - - - ║
                ║■ ■ - - - ║
                ║■ ■ ■ - - ║
                ║■ ■ ■ ■ - ║
                ║■ ■ ■ ■ ■ ║
                ╚══════════╝
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
        return "commonGoalCard-12";
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType12, the Shelf must have at least 15 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 15;
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType12, the Shelf must have five columns of increasing or decreasing height.
     * Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.
     * Tiles can be of any type.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        return checkDescendingStair(shelf) || checkAscendingStair(shelf);
    }


    /**
     * Checks if the Shelf has five columns of decreasing height.
     * @param shelf
     * @return true if the Shelf has five columns of decreasing height, false otherwise.
     */
    public boolean checkDescendingStair(Shelf shelf) {
        boolean patternOne = true;
        boolean patternThree = true;

        for (int col = 0; col < Shelf.COLUMNS; col++) {
            int maxHeightPatternOne = 5 - col;
            int maxHeightPatternThree = 6 - col;
            int countD = 0;
            for (int row = 0; row < Shelf.ROWS; row++) {
                if (shelf.getObjectCard(new Coordinate(row, col)) != null) {
                    countD++;
                }
            }

            patternOne = patternOne && (countD >= maxHeightPatternOne);
            patternThree = patternThree && (countD >= maxHeightPatternThree);

            if (!patternOne && !patternThree) {
                break;
            }
        }
        return patternOne || patternThree;
    }

    /**
     * Checks if the Shelf has five columns of increasing height.
     * @param shelf
     * @return true if the Shelf has five columns of increasing height, false otherwise.
     */
    public boolean checkAscendingStair(Shelf shelf) {
        boolean patternTwo = true;
        boolean patternFour = true;

        for (int col = Shelf.COLUMNS - 1; col >= 0; col--) {
            int maxHeightPatternTwo = col + 1;
            int maxHeightPatternFour = col + 2;
            int countA = 0;
            for (int row = 0; row < Shelf.ROWS; row++) {
                if (shelf.getObjectCard(new Coordinate(row, col)) != null) {
                    countA++;
                }
            }

            patternTwo = patternTwo && (countA >= maxHeightPatternTwo);
            patternFour = patternFour && (countA >= maxHeightPatternFour);

            if (!patternTwo && !patternFour) {
                break;
            }
        }
        return patternTwo || patternFour;
    }
}
