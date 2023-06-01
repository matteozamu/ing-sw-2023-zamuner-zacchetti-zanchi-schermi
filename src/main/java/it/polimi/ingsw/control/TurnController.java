package it.polimi.ingsw.control;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class contains all methods used to manage every single turn of the match.
 */
public class TurnController implements Serializable {

    private static final long serialVersionUID = -598005L;
    private final List<Player> players;
    private Player activePlayer;
    private boolean firstTurn;
    private Game gameInstance;
    private int count;
    private ControllerGame controllerGame;


    public TurnController(List<Player> players, ControllerGame controllerGame) {
        this.gameInstance = Game.getInstance(players.get(0).getName());
        this.players = new ArrayList<>(players);
        this.firstTurn = true;
        this.count = 0;
        this.controllerGame = controllerGame;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * this enumeration rappresents the phases of a single turn
     */
    public enum PhaseType {
        YOUR_TURN, CHOOSE_OBJ_CARD, LOAD_SHELF,
        ORDER_OBJ_CARD
    }
}