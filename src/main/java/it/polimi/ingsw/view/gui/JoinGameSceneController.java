package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.utility.MessageBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Class JoinGameSceneController represents the controller of the join game scene, the scene where the player
 * can see the list of existing games they can join
 */

public class JoinGameSceneController {

    private GuiManager guiManager;

    @FXML
    private Pane mainPane;
    @FXML
    private ImageView exitButton;
    @FXML
    private ImageView backButton;
    @FXML
    private VBox idLabelsBox;

    /**
     * Initializes the scene
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        bindEvents();
    }

    /**
     * Binds click events
     */
    private void bindEvents() {
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onBackButtonClick());
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    /**
     * Handles the back button click
     */
    private void onBackButtonClick() {
        backButton.setDisable(true);
        exitButton.setDisable(true);

        guiManager.closeConnection();
        GuiManager.setLayout(mainPane.getScene(), "fxml/startGameScene.fxml");
    }

    /**
     * Updates the lobby list
     */
    void updateIdList(List<ControllerGame> games) {
        ObservableList<Node> childrens = idLabelsBox.getChildren();
        childrens.clear();

        for (int i = 0; i < games.size(); i++) {
            ControllerGame game = games.get(i);
            Label label = new Label();
            label.setText(game.getGame().getGameName());
            label.getStyleClass().add("idRow");

            final int index = i;
            label.setOnMouseClicked(event -> onLabelClicked(index, games));

            childrens.add(label);
        }

    }

    /**
     * Handles the label click
     *
     * @param index the index of the label
     * @param games the list of games
     */

    private void onLabelClicked(int index, List<ControllerGame> games) {
        backButton.setDisable(true);
        exitButton.setDisable(true);

        if (!guiManager.sendRequest(MessageBuilder.buildJoinGameRequest(guiManager.getClientToken(), guiManager.getUsername(), games.get(index).getId()))) {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);

            guiManager.closeConnection();
            onBackButtonClick();
        }

        LobbySceneController lobbySceneController = GuiManager.setLayout(mainPane.getScene(), "fxml/lobbyScene.fxml");

//        if (lobbySceneController != null) {
//            lobbySceneController.updateLobbyList();
//        }
    }
}
