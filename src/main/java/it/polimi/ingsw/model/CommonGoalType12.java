package it.polimi.ingsw.model;

import java.util.Map;

// OK for TESTING

/**
 * Cinque colonne di altezza crescente o decrescente:
 * a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva
 * deve essere formata da una tessera in più.
 * Le tessere possono essere di qualsiasi tipo.
 */

public final class CommonGoalType12 extends CommonGoal {

    public CommonGoalType12() {
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        return checkDescendingStair(shelf) || checkAscendingStair(shelf);
    }

    private boolean checkDescendingStair(Shelf shelf) {
        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        for (int col = 0; col < shelf.COLUMNS; col++) {
            int maxHeight = 5 - col;
            int countD = 0;
            for (int row = 0; row < shelf.ROWS; row++) {
                if (grid.get(new Coordinate(col, row)) != null) {
                    countD++;
                }
            }
            if (countD != maxHeight) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAscendingStair(Shelf shelf) {
        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        for (int col = shelf.COLUMNS - 1; col >= 0; col--) {
            int maxHeight = col + 1;
            int countA = 0;
            for (int row = 0; row < 6; row++) {
                if (grid.get(new Coordinate(col, row)) != null) {
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
