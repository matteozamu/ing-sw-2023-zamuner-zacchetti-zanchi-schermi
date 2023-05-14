package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class StartWindowController {
    public Pane mainPane;
    @FXML
    private Button startButton;

    @FXML
    public void onStartButtonClick() {
        // Otteniamo la scena corrente
        Scene currentScene = startButton.getScene();

        // Cambiamo la scena corrente alla seconda finestra
        GuiManager.setLayout(currentScene, "fxml/backgroundScene.fxml");

        // Se si vuole chiudere la finestra corrente dopo aver aperto la nuova, si può decommentare la linea seguente
        // Stage currentStage = (Stage) startButton.getScene().getWindow();
        // currentStage.close();
    }
}
