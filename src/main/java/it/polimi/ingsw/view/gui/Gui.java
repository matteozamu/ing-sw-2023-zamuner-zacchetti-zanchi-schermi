package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.StageStyle;


import java.io.InputStream;

/**
 * Class Gui represents the Graphical User Interface
 */
public class Gui extends Application {

    /**
     * Starts the Graphical User Interface
     *
     * @param stage the initial stage
     */
    @Override
    public void start(Stage stage) {
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && !newValue)
                stage.setFullScreen(true);
        });

        InputStream is = Gui.class.getClassLoader().getResourceAsStream("img/logos/nanoTitleV1.png");
        if (is != null) {
            stage.getIcons().add(new Image(is));
        }

        stage.setScene(new Scene(new Pane()));

        GuiManager.setLayout(stage.getScene(), "fxml/menuScene.fxml");
        stage.show();

        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
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
