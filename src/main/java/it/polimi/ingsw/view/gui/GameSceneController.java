package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.GameSerialized;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the graphical interface of the game
 */
public class GameSceneController {

    @FXML
    Pane mainPane;
    @FXML
    StackPane boardArea;
    @FXML
    ImageView map;

    private GuiManager guiManager;

    @FXML
    private void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setGameSceneController(this);

    }

    /**
     * Setups the board and binds all the events
     *
     * @param gameSerialized state of the game at the time of the join
     */
    void setupGame(GameSerialized gameSerialized) {

    }

    /**
     * Called when an error occurs. Displays an alert with the error message
     *
     * @param error message of the error
     */
    void onError(String error) {

    }

    /**
     * Handles the server disconnection
     */
    void onDisconnection() {

    }
}
