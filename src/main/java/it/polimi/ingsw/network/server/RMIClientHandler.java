package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;

public class RMIClientHandler implements ClientHandler, Runnable {
    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void run() {

    }
}
