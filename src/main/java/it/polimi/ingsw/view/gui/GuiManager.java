package it.polimi.ingsw.view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.logging.Logger;

public class GuiManager {
    private static GuiManager instance = null;

    // Aggiungere qui i controller di scena...

    private GuiManager() {
        // Inizializzare qui i controller di scena...
    }

    public static GuiManager getInstance() {
        if (instance == null)
            instance = new GuiManager();
        return instance;
    }

    static <T> T setLayout(Scene scene, String path) {
        FXMLLoader loader = new FXMLLoader(GuiManager.class.getClassLoader().getResource(path));

        Pane pane;
        try {
            pane = loader.load();
            scene.setRoot(pane);
        } catch (IOException e) {
            Logger.getLogger("clientMain").severe(e.getMessage());
            return null;
        }

        return loader.getController();
    }

    // Implementare qui i  metodi per gestire le varie scene...

    public void closeConnection() {
        // Implementare qui la logica per chiudere la connessione...
    }
}
