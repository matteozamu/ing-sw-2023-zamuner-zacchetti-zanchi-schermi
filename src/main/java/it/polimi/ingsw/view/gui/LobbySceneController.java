package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class LobbySceneController {

    private GuiManager guiManager;

    @FXML
    private Pane mainPane;
    @FXML
    private Button backButton;
    @FXML
    private VBox lobbyLabelsBox;

    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setLobbySceneController(this);
    }

    /**
     * Handles an error occurrence
     *
     * @param error message of error
     */
    void onError(String error) {

    }

    /**
     * Handles the server disconnection
     */
    void onDisconnection() {

    }
}
