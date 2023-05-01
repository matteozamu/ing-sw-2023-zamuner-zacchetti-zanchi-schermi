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
    private final Game game;
    private final List<Player> playersQueue;
    private final ControllerGame gameController;
    private Player activePlayer;

    public TurnController(ControllerGame gameController) {
        this.game = gameController.getGame();
        this.playersQueue = new ArrayList<>(game.getPlayers());
        this.activePlayer = playersQueue.get(0);
        this.gameController = gameController;
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
        YOUR_TURN, CHOOSE_OBJ_CARD, CHOOSE_COLUMN,
        ORDER_OBJ_CARD
    }
}