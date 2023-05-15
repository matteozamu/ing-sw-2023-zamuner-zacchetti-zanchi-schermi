package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(false);
        primaryStage.setFullScreen(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); //Evita che l'utente possa uscire dalla modalità fullscreen

        primaryStage.setScene(new Scene(new Pane(), 600, 600)); // 800 è la larghezza, 600 è l'altezza
        GuiManager.setLayout(primaryStage.getScene(), "fxml/startWindow.fxml");
        primaryStage.show();
    }

    @Override
    public void stop() {
        GuiManager.getInstance().closeConnection();
        System.exit(0);
    }
}
