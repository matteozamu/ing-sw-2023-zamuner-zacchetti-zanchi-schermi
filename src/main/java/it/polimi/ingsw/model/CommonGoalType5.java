package it.polimi.ingsw.model;

import java.util.Map;

// OK for TESTING

/**
 * Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso tipo.
 * Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
 */

public class CommonGoalType5 extends CommonGoal {

    public int type = 5;

    public String description = """
            Four groups each containing at least
            4 tiles of the same type (not necessarily
            in the depicted shape).
            The tiles of one group can be different
            from those of another group.""";

    public String cardView = """
            ╔═══════════╗
            ║     ■     ║
            ║     ■     ║
            ║     ■     ║
            ║     ■     ║
            ║     x4    ║
            ╚═══════════╝
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
        return "commonGoalCard-5";
    }

    /**
     * Checks if the Shelf is eligible for the goal check.
     * For CommonGoalType5, the Shelf must have at least 16 object cards.
     *
     * @param shelf The Shelf to check.
     * @return true if the Shelf is eligible, false otherwise.
     */
    @Override
    protected boolean isShelfEligible(Shelf shelf) {
        return shelf.getGrid().size() >= 16;
    }

    @Override
    public boolean checkGoal(Shelf shelf) {
        if (!isShelfEligible(shelf)) {
            return false;
        }

        int groupsCount = 0;

        for (Map.Entry<Coordinate, ObjectCard> entry : shelf.getGrid().entrySet()) {
            Coordinate coordinate = entry.getKey();
            ObjectCard objectCard = entry.getValue();

            if (isValidGroupOfFour(shelf, coordinate, objectCard.getType())) {
                groupsCount++;
            }
        }

        return groupsCount == 4;
    }

    public boolean isValidGroupOfFour(Shelf shelf, Coordinate coord, ObjectCardType type) {
        Coordinate[][] possibleGroups = new Coordinate[][]{
                // Quadrato
                {coord.getAdjacent(Coordinate.Direction.UP), coord.getAdjacent(Coordinate.Direction.RIGHT),
                        coord.getAdjacent(Coordinate.Direction.UP).getAdjacent(Coordinate.Direction.RIGHT)},
                // Fila di quattro tessere
                {coord.getAdjacent(Coordinate.Direction.RIGHT), coord.getAdjacent(Coordinate.Direction.RIGHT).getAdjacent(Coordinate.Direction.RIGHT),
                        coord.getAdjacent(Coordinate.Direction.RIGHT).getAdjacent(Coordinate.Direction.RIGHT).getAdjacent(Coordinate.Direction.RIGHT)},
                // Colonna di quattro tessere
                {coord.getAdjacent(Coordinate.Direction.UP), coord.getAdjacent(Coordinate.Direction.UP).getAdjacent(Coordinate.Direction.UP),
                        coord.getAdjacent(Coordinate.Direction.UP).getAdjacent(Coordinate.Direction.UP).getAdjacent(Coordinate.Direction.UP)}
        };

        for (Coordinate[] group : possibleGroups) {
            boolean isGroupValid = true;
            for (Coordinate groupCoord : group) {
                ObjectCard adjacentCard = shelf.getObjectCard(groupCoord);
                if (adjacentCard == null || adjacentCard.getType() != type) {
                    isGroupValid = false;
                    break;
                }
            }
            if (isGroupValid) {
                return true;
            }
        }

        return false;
    }
}
