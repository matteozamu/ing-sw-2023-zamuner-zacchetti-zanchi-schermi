package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message used to handle a reconnection to the server, it is the response of a ReconnectionRequest
 */
public class ReconnectionMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8073712912335222650L;
    private final String message;
    private final String currentPlayer;

    /**
     * Constructs a message
     *
     * @param message       message to send to the client
     * @param currentPlayer username of the current player
     */
    public ReconnectionMessage(String message, String currentPlayer) {
        super("ServerUser", null, MessageContent.RECONNECTION);
        this.message = message;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the message to send to the client
     *
     * @return message to send to the client
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the username of the current player
     *
     * @return username of the current player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ReconnectionMessage{" +
                "message='" + message + '\'' +
                ", currentPlayer='" + currentPlayer + '\'' +
                '}';
    }
}
