package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSerialized implements Serializable {
    private static final long serialVersionUID = 526685006552543525L;

    private ArrayList<Player> players;
    private Board board;

    // attributes for each single player, initialized thanks to the username passed to the constructor
    private int points;
    private Shelf shelf;
    private PersonalGoalCard personalGoalCard;
    private Map<Coordinate, ObjectCard> limbo;

    public GameSerialized(String userName) {
        Game instance = Game.getInstance();

        if (instance.getPlayers() != null) {
            this.players = new ArrayList<>(instance.getPlayers());
        } else {
            this.players = new ArrayList<>();
        }

        this.board = new Board(instance.getBoard());
        this.limbo = new HashMap<>(instance.getLimbo());

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

    public Map<Coordinate, ObjectCard> getLimbo() {
        return limbo;
    }

    public List<ObjectCard> getAllLimboCards() {
        List<ObjectCard> allLimboCards = new ArrayList<>(limbo.values());

        return allLimboCards;
    }
}
