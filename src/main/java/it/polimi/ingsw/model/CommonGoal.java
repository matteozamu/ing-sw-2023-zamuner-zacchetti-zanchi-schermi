package it.polimi.ingsw.model;

public abstract class CommonGoal {
    private int currentPoints;

    public int updateCurrentPoints(int points){
        // Metodo che aggiorna i punti ottenibili con la CommonGoalCard corrente
        return 0;
    }
    public abstract boolean checkGoal(Shelf shelf);
}