package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

// OK for TESTING

/**
 * Classe che rappresenta un obiettivo comune di tipo 6.
 * L'obiettivo è raggiunto quando ci sono due colonne nella Shelf formate ciascuna da 6 diversi tipi di carte oggetto.
 */
public final class CommonGoalType6 extends CommonGoal {

    public CommonGoalType6() {
    }

    /**
     * Controlla se l'obiettivo comune di tipo 6 è stato raggiunto.
     * L'obiettivo è raggiunto quando ci sono due colonne nella Shelf formate ciascuna da 6 diversi tipi di carte oggetto.
     *
     * @param shelf la Shelf da controllare
     * @return true se l'obiettivo è stato raggiunto, false altrimenti
     */
    @Override
    public boolean checkGoal(Shelf shelf) {
        int columnsWithUniqueObjectCards = 0;

        for (int col = 0; col < shelf.COLUMNS; col++) {
            Map<ObjectCardType, Integer> uniqueObjectCards = new HashMap<>();

            for (int row = 0; row < shelf.ROWS; row++) {
                Coordinate coordinate = new Coordinate(col, row);
                ObjectCard objectCard = shelf.getObjectCard(coordinate);

                if (objectCard != null) {
                    uniqueObjectCards.put(objectCard.getType(), uniqueObjectCards.getOrDefault(objectCard.getType(), 0) + 1);
                }
            }

            if (uniqueObjectCards.size() == 6 && uniqueObjectCards.values().stream().allMatch(count -> count == 1)) {
                columnsWithUniqueObjectCards++;

                if (columnsWithUniqueObjectCards == 2) {
                    return true;
                }
            }
        }

        return false;
    }
}
