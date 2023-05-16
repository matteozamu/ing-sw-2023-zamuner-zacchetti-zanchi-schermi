package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.PossibleAction;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.network.client.DisconnectionListener;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.network.message.Response;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class GuiManager extends ClientGameManager implements DisconnectionListener {
    private static GuiManager instance = null;

    // Aggiungere qui i controller di scena...
    private ConnectionSceneController connectionSceneController;
    private GameSceneController gameSceneController;

    private LobbySceneController lobbySceneController;

    private NumberPlayersController numberPlayersController;

    private GuiManager() {
        // Inizializzare qui i controller di scena...
    }

    public static GuiManager getInstance() {
        if (instance == null)
            instance = new GuiManager();
        return instance;
    }

    /**
     * Sets a layout form FXML file and returns the scene controller
     *
     * @param scene scene where to set the layout
     * @param path  path of the FXML file
     * @param <T>   type of the scene controller
     * @return the scene controller
     */
    static <T> T setLayout(Scene scene, String path) {
        FXMLLoader loader = new FXMLLoader(GuiManager.class.getClassLoader().getResource(path));

        Pane pane;
        try {
            pane = loader.load();
            scene.setRoot(pane);
        } catch (IOException e) {
            Logger.getLogger("myshelfie_client").severe(e.getMessage());
            return null;
        }

        return loader.getController();
    }

    /**
     * Shows a dialog
     *
     * @param window window of the program
     * @param title  title of the dialog
     * @param text   text of the dialog
     */
    static void showDialog(Stage window, String title, String text) {

    }

    // Implementare qui i  metodi per gestire le varie scene...
    void setConnectionSceneController(ConnectionSceneController connectionSceneController) {
        this.connectionSceneController = connectionSceneController;
    }

    void setGameSceneController(GameSceneController gameSceneController) {
        this.gameSceneController = gameSceneController;
    }

    void setLobbySceneController(LobbySceneController lobbySceneController) {
        this.lobbySceneController = lobbySceneController;
    }

    void setNumberPlayersController(NumberPlayersController numberPlayersController) {
        this.numberPlayersController = numberPlayersController;
    }

    @Override
    public void connectionResponse(ConnectionResponse response) {
        Platform.runLater(() ->
                connectionSceneController.onConnectionResponse(response));
    }

    @Override
    public void loadResponse() {
        // Wait others to reconnect
    }

    @Override
    public void lobbyJoinResponse(Response response) {

    }

    @Override
    public void numberOfPlayersRequest(Response response){
        /*
        Platform.runLater(() ->
                numberPlayersController.onNumberResponse(response));

         */
    }

    @Override
    public void notYourTurn(String turnOwner){

    }

    @Override
    public void firstPlayerCommunication(String username, List<CommonGoal> cg){

    }

    @Override
    public void gameStateUpdate(){

    }

    @Override
    public void displayActions(List<PossibleAction> possibleActions){

    }

    @Override
    public void pickBoardCard(){

    }

    @Override
    public void printLimbo(){

    }

    @Override
    public void reorderLimbo(){

    }

    @Override
    public void deleteLimbo(){

    }

    @Override
    public void chooseColumn(){

    }

    @Override
    public void printScore() {

    }

    @Override
    public void gameStateRequest(String username, String token){

    }

    @Override
    public void playersWaitingUpdate(List<String> users){

    }

    @Override
    public void responseError(String error) {
        if (gameSceneController != null) {
            Platform.runLater(() ->
                    gameSceneController.onError(error));
        } else if (lobbySceneController != null) {
            Platform.runLater(() ->
                    lobbySceneController.onError(error));
        } else {
            Platform.runLater(() ->
                    connectionSceneController.onError(error));
        }
    }

    @Override
    public void onDisconnection() {
        Platform.runLater(() -> {
            if (gameSceneController != null) {
                gameSceneController.onDisconnection();
            } else if (lobbySceneController != null) {
                lobbySceneController.onDisconnection();
            }

            System.exit(0);
        });
    }

    public void closeConnection() {
        // Implementare qui la logica per chiudere la connessione...
    }
}
