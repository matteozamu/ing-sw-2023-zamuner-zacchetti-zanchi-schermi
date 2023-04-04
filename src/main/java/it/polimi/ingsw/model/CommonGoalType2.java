package it.polimi.ingsw.model;

// OK for TESTING

/**
 * Cinque tessere dello stesso tipo che formano una diagonale
 */

public final class CommonGoalType2 extends CommonGoal {

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType2, the Shelf must have at least 5 object cards.
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

        for (int row = 0; row < 2; row++) {
            for (int col : new int[] {0, 4}) {
                Coordinate coordinate = new Coordinate(col, row);
                ObjectCardType currentType = shelf.getObjectCard(coordinate).getType();

                if ((col == 0 && checkDiagonalFromTopLeft(shelf, coordinate, currentType)) ||
                        (col == 4 && checkDiagonalFromTopRight(shelf, coordinate, currentType))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalFromTopLeft(Shelf shelf, Coordinate start, ObjectCardType type) {
        for (int i = 0; i < 5; i++) {
            Coordinate currentCoordinate = new Coordinate(start.getColumn() + i, start.getRow() + i);
            ObjectCard currentCard = shelf.getObjectCard(currentCoordinate);

            if (currentCard == null || !currentCard.getType().equals(type)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalFromTopRight(Shelf shelf, Coordinate start, ObjectCardType type) {
        for (int i = 0; i < 5; i++) {
            Coordinate currentCoordinate = new Coordinate(start.getColumn() - i, start.getRow() + i);
            ObjectCard currentCard = shelf.getObjectCard(currentCoordinate);

            if (currentCard == null || !currentCard.getType().equals(type)) {
                return false;
            }
        }
        return true;
    }
}
