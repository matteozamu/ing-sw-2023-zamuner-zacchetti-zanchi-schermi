package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.observer.Observable;

import java.util.logging.Logger;

public abstract class Client extends Observable {
    public static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    /**
     * Sends a message to the server.
     *
     * @param message the message to be sent.
     */
    public abstract void sendMessage(Message message);

    /**
     * Asynchronously reads a message from the server and notifies the ClientController.
     */
    public abstract void readMessage();

    /**
     * Disconnects from the server.
     */
    public abstract void disconnect();

    /**
     * Enable a heartbeat (ping messages) to keep the connection alive.
     *
     * @param enabled set this argument to {@code true} to enable the heartbeat.
     *                set to {@code false} to kill the heartbeat.
     */
    public abstract void enablePinger(boolean enabled);
}
