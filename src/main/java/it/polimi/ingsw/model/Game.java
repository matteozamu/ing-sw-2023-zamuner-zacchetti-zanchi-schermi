package it.polimi.ingsw.model;

import java.util.List;

public class Game {
    public int MAXPLAYER;
    private String id;
    private List<Player> players;
    private Board board;
    private Player firstPlayer;
    private CommonGoal commonGoals[];
    private Player currentPlayer;
    private int numberOfPlayers;
    private List<ObjectCard> cardContainer;

    public void startGame() {
    }
    public void endGame() {
    }

    public Player nextPlayer() {
        return null;
    }

    public boolean isUsernameTaken(String username) {
        return false;
    }

    public int calculatePoints(Player p) {
        return 0;
    }

    private ObjectCard getRandomObjectCard(){
        return null;
    }

    public void fillBoard(){

        // for per scegliere le coordinate, escludere alcuni valori
//        Coordinate c = new Coordinate(x, y);
//        this.board.getGrid().put(c, getRandomObjectCard());
    }

}