package it.polimi.ingsw.control;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This Class contains all methods used to manage every single turn of the match.
 */
public class TurnController implements Serializable {

    /**
     * this enumeration rappresents the phases of a single turn
     */
    public enum PhaseType {
        YOUR_TURN, CHOOSE_OBJ_CARD, CHOOSE_COLUMN,
        ORDER_OBJ_CARD
    }

    private static final long serialVersionUID = -598005L;
    private final Game game;
    private final List<String> nicknameQueue;
    private String activePlayer;
    // private Effect appliedEffect; per ora non serve


    transient Map<String, VirtualView> virtualViewMap;
    private PhaseType phaseType;

    private final ControllerGame gameController;

    /**
     * Constructor of the Turn Controller.
     *
     * @param virtualViewMap Virtual View Map of all Clients.
     * @param gameController Game Controller.
     */
    public TurnController(Map<String, VirtualView> virtualViewMap, ControllerGame gameController) {
        this.game = Game.getInstance();
        this.nicknameQueue = new ArrayList<>(game.getPlayersNicknames());

        this.activePlayer = nicknameQueue.get(0); // set first active player
        this.virtualViewMap = virtualViewMap;
        this.gameController = gameController;
    }

    /**
     * @return the nickname of the active player.
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * Set the active player.
     *
     * @param activePlayer the active Player to be set.
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Broadcast Match Info to all Clients.
     */
    public void broadcastMatchInfo() {
        for (VirtualView vv : virtualViewMap.values()) {
            vv.showMatchInfo(nicknameQueue, game.getBoard(), activePlayer);
        }
    }
}