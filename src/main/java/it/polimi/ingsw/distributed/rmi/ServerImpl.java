package it.polimi.ingsw.distributed.rmi;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.Server;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameView;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private Game model;
    private ControllerGame controller;

    public ServerImpl() throws RemoteException {
        super();
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void register(Client client) throws RemoteException {
        this.model = new Game();
        this.model.addObserver((o, arg) -> {
            try {
                client.update(new GameView(model), arg);
            } catch (RemoteException e) {
                System.out.println("Client error: " + e.getMessage() + ". Skipping the update");
            }
        });

        this.controller = new ControllerGame(model, client);
    }

    @Override
    public void update(Client client, Object arg) throws RemoteException {
        this.controller.update(client, arg);
    }
}
