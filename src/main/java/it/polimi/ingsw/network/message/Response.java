package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;

import java.io.Serial;

/**
 * Generic response to a request
 */
public class Response extends Message {
    @Serial
    private static final long serialVersionUID = 9049719323850459931L;

    private final String message;
    private final MessageStatus status;

    /**
     * Constructs a message
     *
     * @param message message to send to the client
     * @param status  status of the response
     */
    public Response(String message, MessageStatus status) {
        super("ServerUser", null, MessageContent.RESPONSE);
        this.message = message;
        this.status = status;
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
     * Returns the status of the response
     *
     * @return status of the response
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "Response{" +
                "content=" + getContent() +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}