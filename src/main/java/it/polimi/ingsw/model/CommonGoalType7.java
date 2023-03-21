package it.polimi.ingsw.model;

//OK

// Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2.
// Le tessere dei due gruppi devono essere dello stesso tipo.

public class CommonGoalType7 extends CommonGoal {

    public CommonGoalType7() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        for (int row1 = 0; row1 < 5; row1++) {
            for (int col1 = 0; col1 < 4; col1++) {
                Coordinate topLeft1 = new Coordinate(col1, row1);
                ObjectCardType type1 = shelf.getObjectCard(topLeft1) != null ? shelf.getObjectCard(topLeft1).getType() : null;

                if (type1 != null && isSquare(shelf, topLeft1, type1)) {
                    for (int row2 = 0; row2 < 4; row2++) {
                        for (int col2 = 0; col2 < 4; col2++) {
                            if (Math.abs(col1 - col2) > 1 || Math.abs(row1 - row2) > 1) {
                                Coordinate topLeft2 = new Coordinate(col2, row2);
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

        Coordinate topRight = topLeft.getRight();
        Coordinate bottomLeft = topLeft.getDown();
        Coordinate bottomRight = topLeft.getRight().getDown();

        ObjectCard topRightCard = shelf.getObjectCard(topRight);
        ObjectCard bottomLeftCard = shelf.getObjectCard(bottomLeft);
        ObjectCard bottomRightCard = shelf.getObjectCard(bottomRight);

        return type.equals(topRightCard != null ? topRightCard.getType() : null) &&
                type.equals(bottomLeftCard != null ? bottomLeftCard.getType() : null) &&
                type.equals(bottomRightCard != null ? bottomRightCard.getType() : null);
    }


}
