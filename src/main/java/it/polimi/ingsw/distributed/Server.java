package it.polimi.ingsw.distributed;

import java.rmi.Remote;
import java.rmi.RemoteException;

//per estendere con RMI devo estendere l'interfaccia con remote
public interface Server extends Remote {
    /**
     * Register a client to the server
     * @param client the client to register
     */
    void register(Client client) throws RemoteException;

    /**
     * Notify the server that a client has made a choice
     * @param client  the client that generated the event and called the server
     * @param arg     the object sent by the client, it could be a string or something else
     */
    void update(Client client, Object arg) throws RemoteException;
}