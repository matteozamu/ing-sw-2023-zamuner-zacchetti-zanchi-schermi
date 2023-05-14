package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Objects;

public class StartWindowController {
    public Pane mainPane;
    @FXML
    private Button startButton;

    @FXML
    public void initialize() {
        // Creazione di un nuovo oggetto Image
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/sfondo.jpg")).toExternalForm());

        // Creazione di un nuovo BackgroundImage
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);

        // Imposta l'immagine di sfondo per il mainPane
        mainPane.setBackground(new Background(backgroundImage));
    }

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
