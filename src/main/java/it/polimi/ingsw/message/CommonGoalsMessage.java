package it.polimi.ingsw.message;

import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.Game;

import java.util.Arrays;

public class CommonGoalsMessage extends Message {
    private CommonGoal[] commonGoals = new CommonGoal[2];

    public CommonGoalsMessage(CommonGoal[] commonGoal) {
        super(Game.SERVER_NICKNAME, MessageType.COMMON_GOALS);
    }

    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

    @Override
    public String toString() {
        return "CommonGoalsMessage{" +
                "commonGoals=" + Arrays.toString(commonGoals) +
                '}';
    }
}
