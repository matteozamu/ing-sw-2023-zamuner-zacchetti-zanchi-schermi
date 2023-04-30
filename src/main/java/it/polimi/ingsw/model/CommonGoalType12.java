package it.polimi.ingsw.model;

// OK for TESTING

/**
 * Cinque colonne di altezza crescente o decrescente:
 * a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva
 * deve essere formata da una tessera in più.
 * Le tessere possono essere di qualsiasi tipo.
 */

public final class CommonGoalType12 extends CommonGoal {

    public int type = 12;

    public int getType() {
        return type;
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

        for (int col = 0; col < shelf.COLUMNS; col++) {
            int maxHeight = 5 - col;
            int countD = 0;
            for (int row = 0; row < shelf.ROWS; row++) {
                if (shelf.getObjectCard(new Coordinate(col, row)) != null) {
                    countD++;
                }
            }
            if (countD != maxHeight) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAscendingStair(Shelf shelf) {

        for (int col = shelf.COLUMNS - 1; col >= 0; col--) {
            int maxHeight = col + 1;
            int countA = 0;
            for (int row = 0; row < 6; row++) {
                if (shelf.getObjectCard(new Coordinate(col, row)) != null) {
                    countA++;
                }
            }
            if (countA != maxHeight) {
                return false;
            }
        }
        return true;
    }

}
