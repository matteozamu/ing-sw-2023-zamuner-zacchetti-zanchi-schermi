package it.polimi.ingsw.model;

import java.util.Map;

//OK

// Cinque colonne di altezza crescente o decrescente:
// a partire dalla prima colonna a sinistra o a destra, ogni colonna successiva deve essere formata da una tessera in più.
// Le tessere possono essere di qualsiasi tipo.

public class CommonGoalType12 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
        return checkDescendingStair(shelf) || checkAscendingStair(shelf);
    }

    private boolean checkDescendingStair(Shelf shelf) {
        Map<Coordinate, ObjectCard> grid = shelf.getGrid();
        for (int i = 0; i < 5; i++) {
            int maxHeight = 5 - i;
            int countD = 0;
            for (int j = 0; j < 6; j++) {
                if (grid.get(new Coordinate(i, j)) != null) {
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
        for (int i = 4; i >= 0; i--) {
            int maxHeight = i + 1;
            int countA = 0;
            for (int j = 0; j < 6; j++) {
                if (grid.get(new Coordinate(i, j)) != null) {
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
