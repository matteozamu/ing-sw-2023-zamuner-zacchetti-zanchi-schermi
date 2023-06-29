package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

/**
 * Message used to handle a reconnection to the server, it is the response of a ReconnectionRequest
 */
public class ReconnectionMessage extends Message {
    private static final long serialVersionUID = -8073712912335222650L;
    private final String message;
    private final String currentPlayer;

    public ReconnectionMessage(String message, String currentPlayer) {
        super("ServerUser", null, MessageContent.RECONNECTION);
        this.message = message;
        this.currentPlayer = currentPlayer;
    }

    public String getMessage() {
        return message;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        return "ReconnectionMessage{" +
                "message='" + message + '\'' +
                ", currentPlayer='" + currentPlayer + '\'' +
                '}';
    }
}
