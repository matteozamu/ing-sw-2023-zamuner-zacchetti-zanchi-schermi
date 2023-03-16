package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommonCardType11 extends CommonGoal {
// In lavorazione
    @Override
    public boolean checkGoal(@NotNull Shelf shelf) {
        Map<ObjectCardType, Integer> typeCount = new HashMap<>();

        for (Coordinate coord : shelf.getGrid().keySet()) {
            ObjectCard card = shelf.getGrid().get(coord);
            ObjectCardType cardType = card.getType();
            typeCount.put(cardType, typeCount.getOrDefault(cardType, 0) + 1);

            if (typeCount.get(cardType) == 8) {
                return true;
            }
        }

        return false;
    }

}
