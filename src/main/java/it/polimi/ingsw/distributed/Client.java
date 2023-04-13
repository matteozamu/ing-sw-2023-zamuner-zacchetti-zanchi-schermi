package it.polimi.ingsw.distributed;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameView;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param o     The resulting model view
     * @param arg   The causing event
     */
    void update(GameView o, Game.GameState arg) throws RemoteException;
}
