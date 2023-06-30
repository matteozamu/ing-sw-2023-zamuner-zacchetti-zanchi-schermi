package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.RMIClientConnection;
import it.polimi.ingsw.network.message.Message;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

/**
 * This class is the implementation of the interface RMIHandler
 */
public class RMIHandlerImplementation extends UnicastRemoteObject implements RMIHandler {
    @Serial
    private static final long serialVersionUID = 7973004963846163594L;
    private final transient Server server;
    private transient RMIConnection rmiSession;

    /**
     * Constructs a RMIHandlerImplementation
     *
     * @param server server where the client is connected
     * @throws RemoteException in case of problems with communication with client
     */
    RMIHandlerImplementation(Server server) throws RemoteException {
        this.server = server;
    }

    /**
     * Tries to execute the login with the server
     *
     * @param username username used for the login
     * @param client   client connection
     */
    @Override
    public void login(String username, RMIClientConnection client) {
        rmiSession = new RMIConnection(server, client);
        server.login(username, rmiSession);
    }

    /**
     * Sends a message to the server
     *
     * @param message message sent to server
     */
    @Override
    public void onMessage(Message message) {
        server.onMessage(message);
    }

    /**
     * Disconnects the client from the server
     */
    @Override
    public void disconnectMe() {
        rmiSession.disconnect();
    }

    /**
     * Overridden equals method
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RMIHandlerImplementation that = (RMIHandlerImplementation) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(rmiSession, that.rmiSession);
    }

    /**
     * Overridden hashcode method
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), server, rmiSession);
    }
}