package it.polimi.ingsw.model;

// OK for TESTING

/**
 * Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2.
 * Le tessere dei due gruppi devono essere dello stesso tipo.
 */

public final class CommonGoalType7 extends CommonGoal {

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType7, the Shelf must have at least 8 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 8;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        for (int row1 = 0; row1 < shelf.ROWS - 1; row1++) {
            for (int col1 = 0; col1 < shelf.COLUMNS - 1; col1++) {
                Coordinate topLeft1 = new Coordinate(row1, col1);
                ObjectCardType type1 = shelf.getObjectCard(topLeft1) != null ? shelf.getObjectCard(topLeft1).getType() : null;

                if (type1 != null && isSquare(shelf, topLeft1, type1)) {
                    for (int row2 = 0; row2 < shelf.ROWS - 1; row2++) {
                        for (int col2 = 0; col2 < shelf.COLUMNS - 1; col2++) {
                            if (Math.abs(col1 - col2) > 1 || Math.abs(row1 - row2) > 1) {
                                Coordinate topLeft2 = new Coordinate(row2, col2);
                                ObjectCardType type2 = shelf.getObjectCard(topLeft2) != null ? shelf.getObjectCard(topLeft2).getType() : null;

                                if (type1 == type2 && isSquare(shelf, topLeft2, type2)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isSquare(Shelf shelf, Coordinate topLeft, ObjectCardType type) {
        ObjectCard topLeftCard = shelf.getObjectCard(topLeft);

        if (topLeftCard == null || !topLeftCard.getType().equals(type)) {
            return false;
        }

        Coordinate topRight = topLeft.getAdjacent(Coordinate.Direction.RIGHT);
        Coordinate bottomLeft = topLeft.getAdjacent(Coordinate.Direction.DOWN);
        Coordinate bottomRight = topRight.getAdjacent(Coordinate.Direction.DOWN);

        ObjectCard topRightCard = shelf.getObjectCard(topRight);
        ObjectCard bottomLeftCard = shelf.getObjectCard(bottomLeft);
        ObjectCard bottomRightCard = shelf.getObjectCard(bottomRight);

        return type.equals(topRightCard != null ? topRightCard.getType() : null) &&
                type.equals(bottomLeftCard != null ? bottomLeftCard.getType() : null) &&
                type.equals(bottomRightCard != null ? bottomRightCard.getType() : null);
    }
}
