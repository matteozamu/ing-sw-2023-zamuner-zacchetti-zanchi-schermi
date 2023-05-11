package it.polimi.ingsw.model;


/**
 * Cinque tessere dello stesso tipo che formano una diagonale
 */

public final class CommonGoalType2 extends CommonGoal {

    public int type = 2;

    public String description = "Cinque tessere dello stesso tipo che formano una diagonale.";

    public String cardView = """
                ┌──────────┐
                │■         │
                │  ■       │
                │    ■     │
                │      ■   │
                │        ■ │
                └──────────┘
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
        return "CommonGoalType2{" +
                "type=" + type +
                ", description='" + description + '\'' +
                '}';
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
