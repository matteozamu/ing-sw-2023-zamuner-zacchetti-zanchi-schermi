package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;

public class CommonCardType4 extends CommonGoal {

    @Override
    public boolean checkGoal(@NotNull Shelf shelf) {
        return false;
    }
}
