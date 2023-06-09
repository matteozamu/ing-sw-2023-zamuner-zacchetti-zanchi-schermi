package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.InputStream;
/**
 * Class Gui represents the Graphical User Interface
 *
 */

public class Gui extends Application {

    /**
     * Starts the Graphical User Interface
     *
     * @param stage the initial stage
     */
    @Override
    public void start(Stage stage) {
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                stage.setResizable(false);
            }
        });

        InputStream is = Gui.class.getClassLoader().getResourceAsStream("img/logos/gigaTitle.png");
        if (is != null) {
            stage.getIcons().add(new Image(is));
        }

        stage.setScene(new Scene(new Pane()));

        GuiManager.setLayout(stage.getScene(), "fxml/menuScene.fxml");
        stage.show();
    }
     /**
     * Stops the Graphical User Interface
     */
    @Override
    public void stop() {
        GuiManager.getInstance().closeConnection();
        System.exit(0);
    }
}
