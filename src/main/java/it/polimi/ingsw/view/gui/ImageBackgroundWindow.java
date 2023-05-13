package it.polimi.ingsw.view.gui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class ImageBackgroundWindow {
    private Stage stage;
    private Scene scene;
    private String imagePath;

    public ImageBackgroundWindow(String title, String imagePath) {
        this.stage = new Stage();
        this.imagePath = imagePath;

        initializeScene();
        stage.setTitle(title);
        stage.setScene(scene);
    }

    private void initializeScene() {
        // Creazione di un nuovo Pane
        Pane pane = new Pane();

        // Creazione di un nuovo oggetto Image
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResource(imagePath)).toExternalForm());

        // Creazione di un nuovo BackgroundImage
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);

        // Imposta l'immagine di sfondo per il pane
        pane.setBackground(new Background(backgroundImage));

        // Creazione di una nuova scena
        this.scene = new Scene(pane, 800, 600);
    }

    public void show() {
        this.stage.show();
    }

    public void close() {
        this.stage.close();
    }
}
