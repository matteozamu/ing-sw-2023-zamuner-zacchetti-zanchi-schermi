package it.polimi.ingsw.distributed.rmi;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.view.TextualUI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {
    TextualUI view = new TextualUI();

    public ClientImpl(Server server) throws RemoteException {
        super();    // verifico di poterlo costruire anche rispetto alla superclasse
        initialize(server);
    }

    public ClientImpl(Server server, int port) throws RemoteException {
        super(port);
        initialize(server);
    }

    public ClientImpl(Server server, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        initialize(server);
    }

    public void initialize(Server server) throws RemoteException {
        server.register(this);
        view.addObserver((o, arg) -> {
            try {
                server.update(this, arg);
            } catch (RemoteException e) {
                System.out.println("Cannot connect to server: " + e.getMessage() + ". Skipping update");
            }
        });
    }

    @Override
    public void update(GameView o, Game.Event arg){
        view.update(o, arg);
    }

    @Override
    public void run() {
        view.run();
    }
}
