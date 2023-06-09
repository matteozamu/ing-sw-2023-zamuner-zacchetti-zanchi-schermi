package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
/**
 * Class MenuSceneController represents the controller of the menu scene, the first scene of the game
 *
 */

public class MenuSceneController {
    @FXML
    private Pane mainPane;
    @FXML
    private ImageView startButton;

    @FXML
    private ImageView exitButton;

    @FXML
    public void initialize() {
        bindEvents();
    }

    /**
     * Binds on click events
     */
    private void bindEvents() {
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onStartButtonClick());
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    /**
     * Handles the start button click
     */
    private void onStartButtonClick() {
        GuiManager.setLayout(mainPane.getScene(), "fxml/connectionScene.fxml");
    }
}
