package it.polimi.ingsw.model;

/**
 * Cinque colonne di altezza crescente o decrescente:
 * a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva
 * deve essere formata da una tessera in più.
 * Le tessere possono essere di qualsiasi tipo.
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

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCardView() {
        return cardView;
    }

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

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        return checkDescendingStair(shelf) || checkAscendingStair(shelf);
    }

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

            patternOne = patternOne && (countD == maxHeightPatternOne);
            patternThree = patternThree && (countD == maxHeightPatternThree);

            if (!patternOne && !patternThree) {
                break;
            }
        }
        return patternOne || patternThree;
    }

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

            patternTwo = patternTwo && (countA == maxHeightPatternTwo);
            patternFour = patternFour && (countA == maxHeightPatternFour);

            if (!patternTwo && !patternFour) {
                break;
            }
        }
        return patternTwo || patternFour;
    }
}
