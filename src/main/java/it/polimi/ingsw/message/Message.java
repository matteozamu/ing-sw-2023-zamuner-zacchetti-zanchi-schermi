package it.polimi.ingsw.message;

import java.io.Serializable;

/**
 * Abstract message class which must be extended by each message type.
 * Both server and clients will communicate using this generic type of message.
 * This avoids the usage of the "instance of" primitive.
 */
public abstract class Message implements Serializable {
    private static final long serialVersionUID = 6589184250663958343L;

    private final String username;
    private final MessageType messageType;

    Message(String username, MessageType messageType) {
        this.username = username;
        this.messageType = messageType;
    }

    public String getNickname() {
        return username;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "username=" + username +
                ", messageType=" + messageType +
                '}';
    }
}
