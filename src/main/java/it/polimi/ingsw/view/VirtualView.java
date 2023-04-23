package it.polimi.ingsw.view;

import it.polimi.ingsw.message.ErrorMessage;
import it.polimi.ingsw.message.LoginReply;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.observer.Observer;

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
    public void showErrorAndExit(String error) {
        clientHandler.sendMessage(new ErrorMessage(Game.SERVER_NICKNAME, error));
    }

    /**
     * Returns the client handler associated to a client.
     *
     * @return client handler.
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void askUsername() {
        //clientHandler.sendMessage(new LoginReply(false, true));
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
