package it.polimi.ingsw.model;

public abstract class CommonGoal {
    private int currentPoints;

    /**
     * Metodo che aggiorna i punti ottenibili con la CommonGoalCard corrente
     * @param points
     * @return
     */
    public int updateCurrentPoints(int points) {
        return 0;
    }

    /**
     * Metodo astratto padre dei metodi delle classi CommonGoalTypeX
     * @param shelf
     * @return
     */
    public abstract boolean checkGoal(Shelf shelf);
}