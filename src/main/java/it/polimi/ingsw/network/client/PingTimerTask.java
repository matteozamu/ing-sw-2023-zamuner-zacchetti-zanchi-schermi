package it.polimi.ingsw.network.client;

import java.util.TimerTask;

/**
 * This class is a superclass of {@link TimerTask} is executed after {@link Client#DISCONNECTION_TIME} milliseconds which is scheduled by a Timer,
 * once called notifies the client via a call to {@link DisconnectionListener#onDisconnection}
 */
public class PingTimerTask extends TimerTask {
    /**
     * The {@link DisconnectionListener} to notify
     */
    private DisconnectionListener disconnectionListener;

    /**
     * Constructs a {@link PingTimerTask} with the given {@link DisconnectionListener}
     *
     * @param disconnectionListener
     */
    PingTimerTask(DisconnectionListener disconnectionListener) {
        super();
        this.disconnectionListener = disconnectionListener;
    }

    /**
     * Notifies the client via a call to {@link DisconnectionListener#onDisconnection}
     */
    @Override
    public void run() {
        disconnectionListener.onDisconnection();
    }
}
