package it.polimi.ingsw.model;

import it.polimi.ingsw.utility.JsonReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameSerializedTest {
    @Test
    public void testGetBoardMatrix(){
        Game g = Game.getInstance("username1");
        g.addPlayer(new Player("username1", new Shelf(), new PersonalGoalCard(null, "personalGoalCard-1")));
        g.addPlayer(new Player("username2", new Shelf(), new PersonalGoalCard(null, "personalGoalCard-1")));

        Game.getInstanceMap().put("username1", g);
        Game.getInstanceMap().put("username2", g);

        JsonReader.readJsonConstant("GameConstant.json");

        int[][] boardMatrix = JsonReader.getBoard(2);
        GameSerialized gameSerialized = new GameSerialized("username1", "GameConstant.json");
        assertArrayEquals(boardMatrix, gameSerialized.getBoardMatrix());
    }


}