package it.polimi.ingsw.model;

import java.util.List;

public class Game {
    public int MAXPLAYER;
    private String id;
    private List<Player> players;
    private Player winner;
    private Player firstPlayer;
    private CommonGoal commonGoals[];
    private Player currentPlayer;
    private int numberOfPlayers;

    public Game(int MAXPLAYER, String id, List<Player> players, Player winner, Player firstPlayer, CommonGoal[] commonGoals, Player currentPlayer, int numberOfPlayers) {
        this.MAXPLAYER = MAXPLAYER;
        this.id = id;
        this.players = players;
        this.winner = winner;
        this.firstPlayer = firstPlayer;
        this.commonGoals = commonGoals;
        this.currentPlayer = currentPlayer;
        this.numberOfPlayers = numberOfPlayers;
    }

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

    public int getNumberOfPlayers() {
        return 0;
    }

    public int calculatePoints(Player p) {
        return 0;
    }

}