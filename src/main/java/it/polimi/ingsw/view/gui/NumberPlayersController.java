package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import it.polimi.ingsw.utility.MessageBuilder;

public class NumberPlayersController {

    private GuiManager guiManager;

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView twoPlayersButton;
    @FXML
    private ImageView threePlayersButton;
    @FXML
    private ImageView fourPlayersButton;
    @FXML
    private ImageView backButton;

    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setNumberPlayersController(this);

        bindEvents();
    }

    /**
     * Binds on click events
     */
    private void bindEvents() {
        twoPlayersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onNumberClick(2));
        threePlayersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onNumberClick(3));
        fourPlayersButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onNumberClick(4));

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onBackButtonClick());
    }

    /**
     * Handles number of players click
     *
     * @param numberOfPlayers number of players
     */
    private void onNumberClick(int numberOfPlayers) {
        if (!guiManager.sendRequest(MessageBuilder.buildNumberOfPlayerMessage(guiManager.getClientToken(), guiManager.getUsername(), numberOfPlayers))) {

            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);

            guiManager.closeConnection();
            onBackButtonClick();
        }

        LobbySceneController lobbySceneController = GuiManager.setLayout(mainPane.getScene(), "fxml/lobbyScene.fxml");

        if (lobbySceneController != null) {
            lobbySceneController.updateLobbyList();
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
