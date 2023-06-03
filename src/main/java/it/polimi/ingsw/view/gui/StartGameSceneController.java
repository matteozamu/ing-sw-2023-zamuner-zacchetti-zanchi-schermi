package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.utility.MessageBuilder;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartGameSceneController {
    private GuiManager guiManager;

    @FXML
    private Pane mainPane;
    @FXML
    private ImageView createGameButton;
    @FXML
    private ImageView joinGameButton;
    @FXML
    private ImageView backButton;

    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setStartGameSceneController(this);

        bindEvents();
    }

    /**
     * Binds on click events
     */
    private void bindEvents() {
        createGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onCreateGameClick());
        joinGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onJoinGameClick());

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onBackButtonClick());
    }

    /**
     * Handles create game click
     *
     */
    private void onCreateGameClick() {
        if (!guiManager.sendRequest(MessageBuilder.buildCreateGameRequest(guiManager.getUsername(), guiManager.getClientToken()))) {

            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);

            guiManager.closeConnection();
            onBackButtonClick();
        }
        GuiManager.setLayout(mainPane.getScene(), "fxml/numberPlayersScene.fxml");
    }

    /**
     * Handles join game click
     *
     */
    private void onJoinGameClick() {
        if (!guiManager.sendRequest(MessageBuilder.buildListGameRequest(guiManager.getUsername(), guiManager.getClientToken()))) {

            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);

            guiManager.closeConnection();
            onBackButtonClick();
        }
    }

    /**
     * Handles back button click
     */
    private void onBackButtonClick() {
        guiManager.closeConnection();
        GuiManager.setLayout(mainPane.getScene(), "fxml/connectionScene.fxml");
    }
}
