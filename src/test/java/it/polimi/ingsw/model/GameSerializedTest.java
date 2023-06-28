package it.polimi.ingsw.model;

import it.polimi.ingsw.utility.JsonReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameSerializedTest {

    @Test
    public void testGetBoardMatrix(){
        Game g = Game.getInstance("username1");
//        g.addPlayer(new Player("username1", new Shelf(), new PersonalGoalCard(null, "personalGoalCard-1")));
//        g.addPlayer(new Player("username2", new Shelf(), new PersonalGoalCard(null, "personalGoalCard-1")));

//        Game.getInstanceMap().put("username1", g);
//        Game.getInstanceMap().put("username2", g);

        System.out.println(Game.getInstance("username1"));


        JsonReader.readJsonConstant("GameConstant.json");
//        Player player1 = new Player("username1", new Shelf(), new PersonalGoalCard(null, "personalGoalCard-1"));
//        Player player2 = new Player("username2", new Shelf(), new PersonalGoalCard(null, "personalGoalCard-2"));
//        ArrayList<Player> players = new ArrayList<>();
//        players.add(player1);
//        players.add(player2);
        int[][] boardMatrix = JsonReader.getBoard(g.getPlayers().size());
        GameSerialized gameSerialized = new GameSerialized("username1", "GameConstant.json");
        assertArrayEquals(boardMatrix, gameSerialized.getBoardMatrix());
    }


}