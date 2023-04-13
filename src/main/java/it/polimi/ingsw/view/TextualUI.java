package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.util.Observable;

import javax.swing.text.GapContent;
import java.util.Scanner;

public class TextualUI extends Observable<Object> implements Runnable {
    private enum State {
        CHOOSE_OBJECT_CARD,
        WAITING_FOR_PLAYERS,
        VERIFY_COORDINATE, // verifico se la coordinata inserita è valida
        VALID_COORDINATE, // se la coordinata inserita è valida
        INVALID_COORDINATE, //se la coordinata inserita non è valida
        CHOICE_ENDED, //una volta finita la scelta delle object card
    }
    private State state = State.CHOOSE_OBJECT_CARD;
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
            while (getState() == State.WAITING_FOR_PLAYERS) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted while waiting for server: " + e.getMessage());
                    }
                }
            }

        }
    }

    public void update(GameView model, Game.GameState arg){
        switch (arg) {
            case START -> {
                showBoard(model);
                setState(State.INVALID_COORDINATE);
                play();

            }
            case VALID_COORDINATE -> {
                setState(State.VALID_COORDINATE);
                Coordinate c = askObjCard();
                // TODO verifica che siano un massimo di 3
                if (c != null){
                    setState(State.VERIFY_COORDINATE);
                    setChanged();
                    notifyObservers(c);
                } else {
                    setState(State.CHOICE_ENDED);
                    //TODO continuare da qui
                }

            }
        }
    }

    private void play(){
        System.out.println("It's your turn");
        setState(State.CHOOSE_OBJECT_CARD);
        Coordinate c = askObjCard();
        setState(State.VERIFY_COORDINATE);
        setChanged();
        notifyObservers(c);

    }

    private Coordinate askObjCard(){
        Scanner s = new Scanner(System.in);
        String input;
        while (getState() == State.VALID_COORDINATE){
            System.out.println("Do you want to choose another object card? Enter yes or no");{
                if (s.nextLine().toLowerCase().equals("no")) return null;
            }
        }
        System.out.println("Choose an object card, insert a coordinate (ex. 1,2)");
        while (true) {
            input = s.nextLine();
            try {
                String[] parts = input.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                return new Coordinate(x, y);
            } catch (IllegalArgumentException e){
                System.out.println("Bad formatting, insert the cordinate properly");
            }
        }
    }

    private void showBoard(GameView model){
        System.out.println("stampa board");
        // model.printDashBoard();
    }

    public void chooseObjectCard(GameView model){
        model.
    }

    public String askUsername(){
        Scanner s = new Scanner(System.in);
        System.out.println("Inserisci uno username");
        return s.next();
    }

}
