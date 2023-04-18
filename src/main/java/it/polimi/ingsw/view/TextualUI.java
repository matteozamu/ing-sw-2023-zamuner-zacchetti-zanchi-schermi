package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.util.Observable;

import java.util.Scanner;

public class TextualUI extends Observable<Object> implements Runnable {
    private final Object lock = new Object();
    private State state = State.CHOOSE_OBJECT_CARD;

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
        while (true) {
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

    public void update(GameView model, Game.GameState arg) {
        switch (arg) {
            case START -> {
                showBoard(model);
                setState(State.INVALID_COORDINATE);
                play();

            }
            case VALID_COORDINATE -> {
                setState(State.VALID_COORDINATE);

                //verifico che vengano scelte al massimo 3 objcard
//                int ObjCardChoosen = model.getNumberOfObjCardChoosen(); //ritorna il numero di carte scelte, e quindi nel limbo
//                if(ObjCardChoosen >= 3) {
                if (3 >= 3) {
                    setState(State.CHOICE_ENDED);
                    showLimbo(model);
                    int column = chooseColumn(model);
                    setChanged();
                    notifyObservers(column);
                    break;
                }

                Coordinate c = askObjCard();
                if (c != null) {
                    setState(State.VERIFY_COORDINATE);
                    setChanged();
                    notifyObservers(c);
                } else {
                    setState(State.CHOICE_ENDED);
                    showLimbo(model);
                    int column = chooseColumn(model);
                    setChanged();
                    notifyObservers(column);
                }
            }
//            case INVALID_COORDINATE ->
            case COLUMN_CHOSEN -> {
                setState(State.ORDER_OBJ_CARD);
            }
        }
    }

    private void play() {
        System.out.println("It's your turn");
        setState(State.CHOOSE_OBJECT_CARD);
        Coordinate c = askObjCard();
        setState(State.VERIFY_COORDINATE);
        setChanged();
        notifyObservers(c);

    }

    /**
     * method that ask the user to enter the coordinate of the object card he want
     *
     * @return the coordinate of the Object card choosen by the user
     */
    private Coordinate askObjCard() {
        Scanner s = new Scanner(System.in);
        String input;
        while (getState() == State.VALID_COORDINATE) {
            System.out.println("Do you want to choose another object card? Enter yes or no");
            {
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
            } catch (IllegalArgumentException e) {
                System.out.println("Bad formatting, insert the cordinate properly");
            }
        }
    }

    /**
     * method that print the board
     *
     * @param model is the model on which the method is called
     */
    private void showBoard(GameView model) {
        System.out.println("stampa board");
        // model.printDashBoard();
    }

    /**
     * method that show the user the object card selected oh the board
     *
     * @param model is the model on which the method is called
     */
    private void showLimbo(GameView model) {
//        ArrayList<ObjectCardType> limbo = model.getLimbo(); //TODO ritorna l'oggetto limbo
        System.out.println("The object card you choose are: ");
//        for (ObjectCardType objectCard : limbo) {
//            System.out.println(objectCard);
//        }

        /*
        ArrayList<ObjectCard> limbo = model.getLimbo(); //TODO ritorna l'oggetto limbo
        System.out.println("The object card you choose are: ");
        for (ObjectCard objectCard : limbo){
            System.out.println(objectCard.getType());
        }
         */
    }

    /**
     * @param model is the model on which the method is called
     * @return the column number choosen by the user
     */
    private int chooseColumn(GameView model) {
        System.out.println("Choose a column: ");
        // da sostituire con un model.getAvailableColumn
        System.out.println("0, 2, 3");
        // fare il controllo sul numero della colonna
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }

    /**
     * method that ask the username
     *
     * @return the string containing the username
     */
    public String askUsername() {
        Scanner s = new Scanner(System.in);
        System.out.println("Inserisci uno username");
        return s.next();
    }

    private enum State {
        CHOOSE_OBJECT_CARD,
        WAITING_FOR_PLAYERS,
        VERIFY_COORDINATE, // verifico se la coordinata inserita è valida
        VALID_COORDINATE, // se la coordinata inserita è valida
        INVALID_COORDINATE, //se la coordinata inserita non è valida
        CHOICE_ENDED, //una volta finita la scelta delle object card
        ORDER_OBJ_CARD, // l'utente ordina le tessere scelte
    }

}
