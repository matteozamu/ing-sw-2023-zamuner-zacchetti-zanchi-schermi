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
    private final List<String> usernameQueue;
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
        this.usernameQueue = new ArrayList<>(game.getPlayersNicknames());

        this.activePlayer = usernameQueue.get(0); // set first active player
        this.virtualViewMap = virtualViewMap;
        this.gameController = gameController;
    }

    /**
     * Set the current Phase Type.
     *
     * @param turnPhaseType Phase Type.
     */
    public void setPhaseType(PhaseType turnPhaseType) {
        this.phaseType = turnPhaseType;
    }

    /**
     * @return the current Phase Type.
     */
    public PhaseType getPhaseType() {
        return phaseType;
    }

    /**
     * Initialize a new Turn.
     */
    public void newTurn() {
        turnControllerNotify("Turn of " + activePlayer, activePlayer);
        VirtualView vv = virtualViewMap.get(getActivePlayer());
        vv.showGenericMessage("It's your turn!");

//        StorageData storageData = new StorageData();
//        storageData.store(gameController);

        setPhaseType(PhaseType.CHOOSE_OBJ_CARD);
        showBoard(vv);
    }

    /**
     * send a board message that shows the board
     */
    public void showBoard(VirtualView vv){
        vv.showBoard(game.getBoard().getGrid());
    }

    /**
     * Sends a Match Info Message to all the players.
     * Sends a Generic Message which contains Turn Information to all players except the one specified in the second argument.
     *
     * @param messageToNotify Message to send.
     * @param excludeNickname name of the player to be excluded from the broadcast.
     */
    public void turnControllerNotify(String messageToNotify, String excludeNickname) {
        virtualViewMap.entrySet().stream()
                .filter(entry -> !excludeNickname.equals(entry.getKey()))
                .map(Map.Entry::getValue)
                .forEach(vv -> vv.showGenericMessage(messageToNotify));
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

//    /**
//     * Broadcast Match Info to all Clients.
//     */
//    public void broadcastMatchInfo() {
//        for (VirtualView vv : virtualViewMap.values()) {
//            vv.showMatchInfo(usernameQueue, game.getBoard().getGrid(), activePlayer);
//        }
//    }

}