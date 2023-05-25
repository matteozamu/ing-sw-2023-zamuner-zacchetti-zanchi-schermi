package it.polimi.ingsw.model;

/**
 * Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2.
 * Le tessere dei due gruppi devono essere dello stesso tipo.
 */

public final class CommonGoalType7 extends CommonGoal {

    public int type = 7;

    public String description = """
            Two groups each containing 4 tiles of
            the same type in a 2x2 square. The tiles
            of one square can be different from
            those of the other square.""";

    public String cardView = """
            ╔═══════╗
            ║  ■ ■  ║
            ║  ■ ■  ║
            ║  x2   ║
            ╚═══════╝
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
        return "CommonGoalType7{" +
                "type=" + type +
                ", description='" + description + '\'' +
                ", cardView='" + cardView + '\'' +
                '}';
    }

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

        for (int row1 = 0; row1 < Shelf.ROWS - 1; row1++) {
            for (int col1 = 0; col1 < Shelf.COLUMNS - 1; col1++) {
                Coordinate bottomLeft1 = new Coordinate(row1, col1);
                ObjectCardType type1 = shelf.getObjectCard(bottomLeft1) != null ? shelf.getObjectCard(bottomLeft1).getType() : null;

                if (type1 != null && isSquare(shelf, bottomLeft1, type1)) {
                    for (int row2 = 0; row2 < Shelf.ROWS - 1; row2++) {
                        for (int col2 = 0; col2 < Shelf.COLUMNS - 1; col2++) {
                            if (Math.abs(col1 - col2) > 1 || Math.abs(row1 - row2) > 1) {
                                Coordinate bottomLeft2 = new Coordinate(row2, col2);
                                ObjectCardType type2 = shelf.getObjectCard(bottomLeft2) != null ? shelf.getObjectCard(bottomLeft2).getType() : null;

                                if (type1 == type2 && isSquare(shelf, bottomLeft2, type2)) {
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

    public boolean isSquare(Shelf shelf, Coordinate bottomLeft, ObjectCardType type) {
        ObjectCard bottomLeftCard = shelf.getObjectCard(bottomLeft);

        if (bottomLeftCard == null || !bottomLeftCard.getType().equals(type)) {
            return false;
        }

        Coordinate topLeft = bottomLeft.getAdjacent(Coordinate.Direction.UP);
        Coordinate topRight = topLeft.getAdjacent(Coordinate.Direction.RIGHT);
        Coordinate bottomRight = bottomLeft.getAdjacent(Coordinate.Direction.RIGHT);

        ObjectCard topRightCard = shelf.getObjectCard(topRight);
        ObjectCard topLeftCard = shelf.getObjectCard(topLeft);
        ObjectCard bottomRightCard = shelf.getObjectCard(bottomRight);

        return type.equals(topRightCard != null ? topRightCard.getType() : null) &&
                type.equals(topLeftCard != null ? topLeftCard.getType() : null) &&
                type.equals(bottomRightCard != null ? bottomRightCard.getType() : null);
    }
}
