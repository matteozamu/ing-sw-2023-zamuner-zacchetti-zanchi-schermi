package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSerialized implements Serializable {
    private static final long serialVersionUID = 526685006552543525L;

    private ArrayList<Player> players;
    private Board board;

    // attributes for each single player, initialized thanks to the username passed to the constructor
    private int points;
    private Shelf shelf;
    private PersonalGoalCard personalGoalCard;

    public GameSerialized(String userName) {
        Game instance = Game.getInstance();

        if (instance.getPlayers() != null) {
            this.players = new ArrayList<>(instance.getPlayers());
        } else {
            this.players = new ArrayList<>();
        }

        this.board = new Board(instance.getBoard());

        Player player = Game.getInstance().getPlayerByName(userName);
        this.shelf = player.getShelf();
        this.personalGoalCard = player.getPersonalGoalCard();
        this.points = player.getCurrentPoints();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPoints() {
        return this.points;
    }

    public List<Player> getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>(players);

        return allPlayers;
    }

    public Board getBoard() {
        return board;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }
}
