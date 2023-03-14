package it.polimi.ingsw.model;

import java.util.List;

public class Game {
    public int MAXPLAYER;
    private String id;
    private List<Player> players;
    private Board board;
    private Player winner;
    private Player firstPlayer;
    private CommonGoal commonGoals[];
    private Player currentPlayer;
    private int numberOfPlayers;

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

}