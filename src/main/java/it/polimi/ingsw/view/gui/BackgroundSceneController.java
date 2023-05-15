package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Objects;

public class BackgroundSceneController {
    @FXML
    private Pane mainPane; // è il fx:id del pane di backgroundScene.fxml

    @FXML
    public void initialize() {
        // Creazione di un nuovo oggetto Image
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource("img/boards/livingroom.png")).toExternalForm());

        // Creazione di un nuovo BackgroundImage
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);

        // Imposta l'immagine di sfondo per il mainPane
        mainPane.setBackground(new Background(backgroundImage));
    }
}
