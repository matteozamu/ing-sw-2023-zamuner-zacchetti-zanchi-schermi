package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.utility.MessageBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class NumberPlayersController represents the controller of the scene where the player can choose
 * the number of players of the game
 */
public class NumberPlayersController {

    private GuiManager guiManager;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField gameNameField;
    @FXML
    private ImageView twoPlayersButton;
    @FXML
    private ImageView threePlayersButton;
    @FXML
    private ImageView fourPlayersButton;
    @FXML
    private ImageView backButton;

    /**
     * Initializes the scene
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();

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
        final String gameName = gameNameField.getText();

        if (!guiManager.sendRequest(MessageBuilder.buildNumberOfPlayerMessage(guiManager.getClientToken(), guiManager.getUsername(), numberOfPlayers, gameName))) {

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
