package it.polimi.ingsw.model;

// Due colonne formate ciascuna da 6 diversi tipi di tessere.

//TODO : Da verificare

import java.util.HashMap;
import java.util.Map;

public class CommonGoalType6 extends CommonGoal {

    public CommonGoalType6() {
    }


    @Override
    public boolean checkGoal(Shelf shelf) {
        Map<ObjectCardType, Integer> typeCount = new HashMap<>();

        for(int col = 0; col < 4; col++){
            for(int row = 0; row < 5; row ++){
                Coordinate coord = new Coordinate(col, row);
                ObjectCard card = shelf.getGrid().get(coord);
                ObjectCardType cardType = null;
                if(card != null){
                    cardType = card.getType();
                    typeCount.put(cardType, typeCount.getOrDefault(cardType, 0) + 1);
                }

                if (typeCount.get(cardType) != 1) {
                    return false;
                }
            }
        }

        return true;
    }
}
