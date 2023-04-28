package it.polimi.ingsw.network.message;

import it.polimi.ingsw.utility.MessageContent;

/**
 * Message class of connection request
 */
public class ConnectionRequest extends Message {
    private static final long serialVersionUID = 5444683484323330868L;

    public ConnectionRequest(String username) {
        super(username, null, MessageContent.CONNECTION);
    }

    @Override
    public String toString() {
        return "ConnectionRequest{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() +
                "}";
    }
}
