package it.polimi.ingsw.network.client;


import it.polimi.ingsw.network.message.Message;

import java.util.List;

/**
 * This class run a process that wait messages from the server
 * and notify the Client whenever one arrives
 */
public class ClientUpdater implements Runnable {
    private final Client client;
    private ClientUpdateListener updateListener;
    private Thread thread;

    ClientUpdater(Client client, ClientUpdateListener updateListener) {
        this.client = client;
        this.updateListener = updateListener;
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Runs a process
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (client) {
                List<Message> messages;

                do {
                    messages = client.receiveMessages();
                    try {
                        client.wait(100);
                    } catch (InterruptedException e) {
                        ClientGameManager.LOGGER.severe(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                } while (messages.isEmpty());

                messages.forEach(updateListener::onUpdate);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Stop the running process
     */
    public void stop() {
        this.thread.interrupt();
    }

    /**
     * Start the process
     */
    public void start() {
        if (this.thread.isInterrupted()) {
            this.thread.start();
        }
    }
}