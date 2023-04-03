package it.polimi.ingsw;

import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.distributed.rmi.ServerImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AppServerRMI {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();

        // ottengo un registro locale dei metodi, sulla porta 1099
        Registry registry = LocateRegistry.getRegistry();
        // se qualcuno chiede un "server", rmi crea una connessione con uno stub server
        registry.rebind("server", server);
    }
}
