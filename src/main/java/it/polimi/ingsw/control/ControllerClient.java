package it.polimi.ingsw.control;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerClient implements ViewObserver, Observer {
    private final View view;

    private Client client;
    private String username;

    private final ExecutorService taskQueue;
    public ControllerClient(View view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void onUpdateUsername(String username) {
        this.username = username;
        System.out.println("ciao "+ username);
        //client.sendMessage(new LoginRequest(this.nickname));
    }
}
