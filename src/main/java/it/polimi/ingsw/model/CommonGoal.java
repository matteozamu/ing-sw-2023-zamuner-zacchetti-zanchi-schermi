package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

public abstract class CommonGoal {
    private int currentPoints;

    public int updateCurrentPoints(int points){
        // Metodo che aggiorna i punti ottenibili con la CommonGoalCard corrente
        return 0;
    }
    public abstract boolean checkGoal(@NotNull Shelf shelf);
}