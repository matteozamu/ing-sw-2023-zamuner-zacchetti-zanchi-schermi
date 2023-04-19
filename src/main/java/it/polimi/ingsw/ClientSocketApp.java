package it.polimi.ingsw;

public class ClientSocketApp {
    public static void main(String[] args) {
        Cli view = new Cli();
        ClientController clientcontroller = new ClientController(view);
        view.addObserver(clientcontroller);
        view.init();
    }
}
