package it.polimi.ingsw.control;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.PlayersNumberReply;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.VirtualView;

import java.io.Serializable;
import java.util.Map;

/**
 * This Class verifies that all messages sent by client contain valid information.
 */
public class InputController implements Serializable {
    private static final long serialVersionUID = 7413156215358698632L;

    private final Game game;
    private transient Map<String, VirtualView> virtualViewMap;
    private final ControllerGame gameController;

    /**
     * Constructor of the Input Controller Class.
     *
     * @param virtualViewMap Virtual View Map.
     * @param gameController Game Controller.
     */
    public InputController(Map<String, VirtualView> virtualViewMap, ControllerGame gameController) {
        this.game = Game.getInstance();
        this.virtualViewMap = virtualViewMap;
        this.gameController = gameController;
    }

    /**
     * Verify data sent by client to server.
     *
     * @param message Message from Client.
     * @return {code @true} if Message contains valid data {@code false} otherwise.
     */
    public boolean verifyReceivedData(Message message) {
        switch (message.getMessageType()){
            case PLAYERS_NUMBER_REPLY:
                return playerNumberReplyCheck(message);
            default: // Never should reach this statement.
                return false;
        }
    }

    //TODO da fare la funzione check login con i metodi da noi creati
    /**
     * Check if a username is valid or not.
     *
     * @param username new client's username.
     * @param view     view for active client.
     * @return {code @true} if it's a valid username {code @false} otherwise.
     */
    public boolean checkLoginUsername(String username, View view) {
        if (username.isEmpty() || username.equalsIgnoreCase(Game.SERVER_NICKNAME)) {
            view.showGenericMessage("Forbidden name.");
            view.showLoginResult(false, true, null);
            return false;
        } else if (gameController.isUsernameAvailable(username)) {
            view.showGenericMessage("username already taken.");
            view.showLoginResult(false, true, null);
            return false;
        }
        return true;
    }

    private boolean playerNumberReplyCheck(Message message){
        PlayersNumberReply playerNumberReply = (PlayersNumberReply) message;

        if (playerNumberReply.getPlayerNumber() < 5 && playerNumberReply.getPlayerNumber() > 1) {
            return true;
        } else {
            VirtualView virtualView = virtualViewMap.get(message.getUsername());
            virtualView.askPlayersNumber();
            return false;
        }
    }
}
