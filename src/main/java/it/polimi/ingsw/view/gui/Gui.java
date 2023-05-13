// Gui.java
package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Gui extends Application {
    @Override
    public void start(Stage stage) {
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Button startButton = new Button("Start");
        startButton.setOnAction(event -> {
            ImageBackgroundWindow imageWindow = new ImageBackgroundWindow(
                    "Background",
                    "img/sfondo.jpg");

            imageWindow.show();
        });

        // StackPane (un layout che centra il suo contenuto)
        StackPane root = new StackPane();
        root.getChildren().add(startButton);

        // nuova scena con il root pane e mostra la scena
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        GuiManager.getInstance().closeConnection();
        System.exit(0);
    }
}
