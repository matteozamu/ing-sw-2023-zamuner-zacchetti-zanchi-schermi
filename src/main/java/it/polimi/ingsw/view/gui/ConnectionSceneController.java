package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.network.message.ConnectionResponse;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ConnectionSceneController {

    private GuiManager guiManager;

    @FXML
    private Pane mainPane;

    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setConnectionSceneController(this);
    }

    /**
     * Handles the connection response
     *
     * @param response connection response
     */
    void onConnectionResponse(ConnectionResponse response) {

    }

    /**
     * Handles an error occurrence
     *
     * @param error message of error
     */
    void onError(String error) {

    }
}
