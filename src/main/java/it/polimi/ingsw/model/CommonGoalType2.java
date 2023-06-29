package it.polimi.ingsw.model;


/**
 * Five tiles of the same type forming a diagonal.
 */

public final class CommonGoalType2 extends CommonGoal {

    public int type = 2;

    public String description = "Five tiles of the same type forming a diagonal.";

    public String cardView = """
                ╔══════════╗
                ║■         ║
                ║  ■       ║
                ║    ■     ║
                ║      ■   ║
                ║        ■ ║
                ╚══════════╝
                """;

    /**
     * Gets the type of the CommonGoal.
     * @return The type of the CommonGoal.
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * Gets the description of the CommonGoal.
     *  @return The description of the CommonGoal.
     */

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the visual representation of the CommonGoal.
     * @return The view of the CommonGoal.
     */
    @Override
    public String getCardView() {
        return cardView;
    }

    /**
     * Gets the String representation of the CommonGoal.
     * @return The String representation of the CommonGoal.
     */

    @Override
    public String toString() {
        return "commonGoalCard-2";
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

    /**
     * Checks if the given Shelf satisfies the CommonGoal.
     * For CommonGoalType2, the Shelf must contain 5 object cards of the same type forming a diagonal.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf satisfies the CommonGoal, false otherwise.
     */

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        for (int row = 5; row >= 4; row--) {
            for (int col : new int[]{0, 4}) {
                Coordinate coordinate = new Coordinate(row, col);
                ObjectCard objectCard = shelf.getObjectCard(coordinate);

                if (objectCard == null) {
                    continue;
                }

                ObjectCardType currentType = objectCard.getType();

                if ((col == 0 && checkDiagonalFromTopLeft(shelf, coordinate, currentType)) ||
                        (col == 4 && checkDiagonalFromTopRight(shelf, coordinate, currentType))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given Shelf contains 5 object cards of the same type forming a diagonal from the top left.
     *
     * @param shelf The Shelf to check.
     * @param start The Coordinate of the first ObjectCard to check.
     * @param type  The ObjectCardType to check.
     * @return true if the Shelf contains 5 object cards of the same type forming a diagonal from the top left, false otherwise.
     */

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

    /**
     * Checks if the given Shelf contains 5 object cards of the same type forming a diagonal from the top right.
     *
     * @param shelf The Shelf to check.
     * @param start The Coordinate of the first ObjectCard to check.
     * @param type  The ObjectCardType to check.
     * @return true if the Shelf contains 5 object cards of the same type forming a diagonal from the top right, false otherwise.
     */

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
