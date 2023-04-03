package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.util.Observable;

import javax.swing.text.View;
import java.util.Scanner;

public class TextualUI extends Observable<Object> implements Runnable {
    private enum State {
        WAITING_FOR_PLAYER,
        WAITING_FOR_OUTCOME
    }
    private State state = State.WAITING_FOR_PLAYER;
    private final Object lock = new Object();

    private State getState() {
        synchronized (lock) {
            return state;
        }
    }

    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    public void createPlayer() {
        System.out.print("Write here your username: ");
        Scanner s = new Scanner(System.in);
        String input = s.next();
    }

    @Override
    public void run() {
        while (true){
            while (getState() == State.WAITING_FOR_OUTCOME) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted while waiting for server: " + e.getMessage());
                    }
                }
            }
            System.out.println("il programma duplica la stringa");
            String s = askUsername();
            setState(State.WAITING_FOR_OUTCOME);
            setChanged();
            notifyObservers(s);
        }
    }

    public void update(GameView model, Game.Event arg){
        switch (arg) {
            /*
            case OUTCOME -> {
                showStringaConcatenata(model);
                this.setState(State.WAITING_FOR_PLAYER);
            }
            case PLAYER_IN -> showStringa(model);
             */
        }
    }

    public String askUsername(){
        Scanner s = new Scanner(System.in);
        System.out.println("Inserisci uno username");
        return s.next();
    }

}
