package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

/**
 * This class represents a Client
 */
public abstract class Client extends UnicastRemoteObject {
    public static final int MAX_USERNAME_LENGTH = 20;
    static final int DISCONNECTION_TIME = 15000;
    private static final long serialVersionUID = -5831202245262756797L;
    final transient List<Message> messageQueue;
    private final String username;
    private final String address;
    private final int port;
    transient DisconnectionListener disconnectionListener;
    transient Timer pingTimer;
    private String token;

    /**
     * Constructs a client
     *
     * @param username username of the player
     * @param address  address of the server
     * @param port     port of the server
     * @throws RemoteException in case of problems with communication with server
     */
    Client(String username, String address, int port, DisconnectionListener disconnectionListener) throws RemoteException {
        this.username = username;
        this.address = address;
        this.port = port;
        this.disconnectionListener = disconnectionListener;

        this.messageQueue = new ArrayList<>();

        this.pingTimer = new Timer();
    }

    /**
     * @return the address of the server
     */
    String getAddress() {
        return address;
    }

    /**
     * @return the port of the server
     */
    public int getPort() {
        return port;
    }

    /**
     * Starts a connection with the server
     *
     * @throws Exception in case of problems with communication with server
     */
    public abstract void startConnection() throws Exception;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token
     *
     * @param token token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Sends a message to the server
     *
     * @param message message to send to the server
     * @throws IOException in case of problems with communication with server
     */
    public abstract void sendMessage(Message message) throws IOException;

    /**
     * Closes connection with the server
     *
     * @throws Exception in case of problems with communication with server
     */
    public abstract void close() throws Exception;

    /**
     * @return the list of messages in the queue
     */
    List<Message> receiveMessages() {
        ArrayList<Message> copyList;

        synchronized (messageQueue) {
            copyList = new ArrayList<>(List.copyOf(messageQueue));
            messageQueue.clear();
        }

        return copyList;
    }

    /**
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * overridden equals method
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return port == client.port &&
                Objects.equals(disconnectionListener, client.disconnectionListener) &&
                Objects.equals(pingTimer, client.pingTimer) &&
                Objects.equals(username, client.username) &&
                Objects.equals(address, client.address) &&
                Objects.equals(token, client.token) &&
                Objects.equals(messageQueue, client.messageQueue);
    }

    /**
     * overridden hashcode method
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), disconnectionListener, pingTimer, username, address, port, token, messageQueue);
    }
}
