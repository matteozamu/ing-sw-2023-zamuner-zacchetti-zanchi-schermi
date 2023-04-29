package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

// OK for TESTING

/**
 * Classe che rappresenta un obiettivo comune di tipo 6.
 * L'obiettivo è raggiunto quando ci sono due colonne nella Shelf formate ciascuna da 6 diversi tipi di carte oggetto.
 */
public final class CommonGoalType6 extends CommonGoal {

    public int type = 6;

    public int getType() {
        return type;
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType6, the Shelf must have at least 12 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 12;
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
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int columnsWithUniqueObjectCards = 0;

        for (int col = 0; col < shelf.COLUMNS; col++) {
            Map<ObjectCardType, Integer> uniqueObjectCards = new HashMap<>();

            for (int row = 0; row < shelf.ROWS; row++) {
                Coordinate coordinate = new Coordinate(row, col);
                ObjectCard objectCard = shelf.getObjectCard(coordinate);

                if (objectCard != null) {
                    ObjectCardType cardType = objectCard.getType();
                    uniqueObjectCards.put(cardType, uniqueObjectCards.getOrDefault(cardType, 0) + 1);
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
