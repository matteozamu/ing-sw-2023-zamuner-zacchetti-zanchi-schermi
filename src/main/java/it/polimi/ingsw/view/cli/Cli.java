package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;


/**
 * This class offers a User Interface to the user via terminal. It is an implementation of the {@link View}.
 */
public class Cli extends ViewObservable implements View {

    public void init(){
        System.out.println("Welcome to MyShelfie");
        askUsername();
//        Map<String, String> serverInfo = new HashMap<>();
//        String defaultAddress = "localhost";
//        String defaultPort = "16847";
//        System.out.println("Please specify the following settings. The default value is shown between brackets.");
//        // print default value
//
//        serverInfo.put("address", defaultAddress);
//        serverInfo.put("port", defaultPort);
//
//        notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo));
    }

    @Override
    public void askUsername() {
        Scanner s = new Scanner(System.in);
        String username = null;
        do {
            System.out.println("Enter your username: ");
            username = s.nextLine();
        } while (username == null);

        String finalUsername = username;
        notifyObserver(obs -> obs.onUpdateUsername(finalUsername));
    }

}