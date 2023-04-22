package it.polimi.ingsw.message;

/**
 * Message used to keep the connection alive.
 */
public class PingMessage extends Message {

    private static final long serialVersionUID = 87654321L;

    public PingMessage() {
        super(null, MessageType.PING);
    }
}
