package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.network.message.Response;
import it.polimi.ingsw.utility.MessageBuilder;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Class StartGameSceneController represents the controller of the scene where the player can choose to create
 * a new game or to join an existing one
 */

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

    /**
     * Initializes the scene
     */
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
     */
    private void onCreateGameClick() {
//        createGameButton.setDisable(true);
//        joinGameButton.setDisable(true);
//        backButton.setDisable(true);

        if (!guiManager.sendRequest(MessageBuilder.buildCreateGameRequest(guiManager.getUsername(), guiManager.getClientToken()))) {

            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);

            guiManager.closeConnection();
            onBackButtonClick();
        }
        //GuiManager.setLayout(mainPane.getScene(), "fxml/numberPlayersScene.fxml");
    }

    void numberOfPlayerRequest(Response response) {
        GuiManager.setLayout(mainPane.getScene(), "fxml/numberPlayersScene.fxml");
    }

    /**
     * Handles join game click
     */
    private void onJoinGameClick() {
//        createGameButton.setDisable(true);
//        joinGameButton.setDisable(true);
//        backButton.setDisable(true);

        if (!guiManager.sendRequest(MessageBuilder.buildListGameRequest(guiManager.getUsername(), guiManager.getClientToken()))) {

            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);

            guiManager.closeConnection();
            onBackButtonClick();
        }
    }

    /**
     * Handles the situation where the player chooses to join an existing game
     */
    void onJoinGameResponse(List<ControllerGame> games) {
        JoinGameSceneController joinGameSceneController = GuiManager.setLayout(mainPane.getScene(), "fxml/joinGameScene.fxml");

        if (joinGameSceneController != null) {
            joinGameSceneController.updateIdList(games);
        }
    }

    /**
     * Handles the response of a lobby join request. Depending on the response status, the GUI is
     * either moved to a different scene or an error dialog is shown.
     *
     * @param response response of the join request
     */
    void onLobbyJoinResponse(Response response) {
        if (response.getStatus() == MessageStatus.ERROR) {

            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    response.getMessage());

            onBackButtonClick();

        } else {
            if (guiManager.getLobbyPlayers().size() == 1) {
                GuiManager.setLayout(mainPane.getScene(), "fxml/numberPlayersScene.fxml");
            } else {
                LobbySceneController lobbySceneController = GuiManager.setLayout(mainPane.getScene(), "fxml/lobbyScene.fxml");

                if (lobbySceneController != null) {
                    lobbySceneController.updateLobbyList();
                }
            }
        }
    }

    /**
     * Handles the situation where the player chooses to join an existing game but there are no available games
     */
    void noGameAvailable() {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                "No games available");

        createGameButton.setDisable(false);
        joinGameButton.setDisable(false);
        backButton.setDisable(false);
    }

    /**
     * Handles back button click
     */
    private void onBackButtonClick() {
        createGameButton.setDisable(true);
        joinGameButton.setDisable(true);
        backButton.setDisable(true);

        guiManager.closeConnection();
        GuiManager.setLayout(mainPane.getScene(), "fxml/connectionScene.fxml");
    }
}
