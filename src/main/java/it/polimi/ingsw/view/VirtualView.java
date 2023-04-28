package it.polimi.ingsw.view;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ObjectCard;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

import java.util.List;
import java.util.Map;

// questa classe è quella che "genera i messaggi"

/**
 * Hides the network implementation from the controller.
 * The controller calls methods from this class as if it was a normal view.
 * Instead, a network protocol is used to communicate with the real view on the client side.
 */
public class VirtualView implements View, Observer {
    private final ClientHandler clientHandler;

    /**
     * Default constructor.
     *
     * @param clientHandler the client handler the virtual view must send messages to.
     */
    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        clientHandler.sendMessage(new LoginReply(nicknameAccepted, connectionSuccessful));
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        clientHandler.sendMessage(new GenericMessage(genericMessage));
    }

    @Override
    public void showErrorAndExit(String error) {
        clientHandler.sendMessage(new ErrorMessage(Game.SERVER_NICKNAME, error));
    }

    @Override
    public void showLobby(List<String> nicknameList, int numPlayers) {
        clientHandler.sendMessage(new LobbyMessage(nicknameList, numPlayers));
    }

    /**
     * returns the client handler associated to a client.
     *
     * @return client handler.
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    /**
     * send a new login reply message
     */
    @Override
    public void askUsername() {
        clientHandler.sendMessage(new LoginReply(false, true));
    }

    /**
     * send a message with the number of player the user wants to play with
     */
    @Override
    public void askPlayersNumber() {
        clientHandler.sendMessage(new PlayersNumberRequest());
    }

    /** shows match info such as the board
     *
     * @param players is the list of players in the game
     * @param board is the game board
     * @param activePlayer is the player of the current turn
     */
    @Override
    public void showMatchInfo(List<String> players, Map<Coordinate, ObjectCard> board, String activePlayer) {
        clientHandler.sendMessage(new BoardMessage(Game.SERVER_NICKNAME, MessageType.BOARD, players, board, activePlayer));
    }

    @Override
    public void askObjCard(){

    }


    /**
     * Receives an update message from the model.
     * The message is sent over the network to the client.
     * The proper action based on the message type will be taken by the real view on the client.
     *
     * @param message the update message.
     */
    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }
}
