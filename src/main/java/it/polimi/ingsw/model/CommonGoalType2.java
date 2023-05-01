package it.polimi.ingsw.model;


/**
 * Cinque tessere dello stesso tipo che formano una diagonale
 */

public final class CommonGoalType2 extends CommonGoal {

    public int type = 2;

    public int getType() {
        return type;
    }

    /**
     * Returns a string representation of the common goal, describing its requirements and conditions.
     *
     * @return A string representing the common goal.
     */
    @Override
    public String toString() {
        return "Cinque tessere dello stesso tipo che formano una diagonale.";
    }

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

        for (int row = 5; row >= 4; row--) {
            for (int col : new int[] {0, 4}) {
                Coordinate coordinate = new Coordinate(row, col);
                ObjectCardType currentType = shelf.getObjectCard(coordinate).getType();

                if ((col == 0 && checkDiagonalFromTopLeft(shelf, coordinate, currentType)) ||
                        (col == 4 && checkDiagonalFromTopRight(shelf, coordinate, currentType))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkDiagonalFromTopLeft(Shelf shelf, Coordinate start, ObjectCardType type) {
        for (int i = 0; i < 5; i++) {
            Coordinate currentCoordinate = new Coordinate(start.getRow() - i, start.getColumn() + i);
            ObjectCard currentCard = shelf.getObjectCard(currentCoordinate);

            if (currentCard == null || !currentCard.getType().equals(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDiagonalFromTopRight(Shelf shelf, Coordinate start, ObjectCardType type) {
        for (int i = 0; i < 5; i++) {
            Coordinate currentCoordinate = new Coordinate(start.getRow() - i, start.getColumn() - i);
            ObjectCard currentCard = shelf.getObjectCard(currentCoordinate);

            if (currentCard == null || !currentCard.getType().equals(type)) {
                return false;
            }
        }
        return true;
    }
}
