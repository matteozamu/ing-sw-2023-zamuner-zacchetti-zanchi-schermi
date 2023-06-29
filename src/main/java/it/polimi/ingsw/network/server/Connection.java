package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;

/**
 * This interface that represents a connection with a client
 */
public abstract class Connection {
    private boolean connected = true;
    private String token;

    /**
     * @return the connection status
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Sends a message to the client
     *
     * @param message message to send to the client
     * @throws IOException in case of problems with communication with client
     */
    public abstract void sendMessage(Message message) throws IOException;

    /**
     * Disconnects from the client
     */
    public abstract void disconnect();

    /**
     * Sends a ping message to client
     */
    public abstract void ping();

    /**
     * gest the token of the connection
     */
    public String getToken() {
        return token;
    }

    /**
     * sets the token of the connection
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
