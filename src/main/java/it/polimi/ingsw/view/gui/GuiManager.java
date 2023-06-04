package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.PossibleAction;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.GameSerialized;
import it.polimi.ingsw.network.client.ClientGameManager;
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

    private StartGameSceneController startGameSceneController;

    private JoinGameSceneController joinGameSceneController;

    private GuiManager() {
        super();
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
        FXMLLoader loader = new FXMLLoader(GuiManager.class.getClassLoader().getResource("fxml/dialogScene.fxml"));

        Scene dialogScene;
        try {
            dialogScene = new Scene(loader.load(), 600, 300);
        } catch (IOException e) {
            Logger.getLogger("myshelfie_client").severe(e.getMessage());
            return;
        }

        Stage dialog = new Stage();
        dialog.setScene(dialogScene);
        dialog.initOwner(window);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setAlwaysOnTop(true);

        dialogScene.lookup("#okButton").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> dialog.close());

        ((Label) dialogScene.lookup("#dialogTitle")).setText(title);
        ((Label) dialogScene.lookup("#dialogText")).setText(text);

        dialog.showAndWait();
    }

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

    void setStartGameSceneController(StartGameSceneController startGameSceneController) {
        this.startGameSceneController = startGameSceneController;
    }

    void setJoinGameSceneController(JoinGameSceneController joinGameSceneController) {
        this.joinGameSceneController = joinGameSceneController;
    }

    @Override
    public void connectionResponse(ConnectionResponse response) {
        Platform.runLater(() ->
                connectionSceneController.onConnectionResponse(response));
    }

    @Override
    public void addPlayerToGameRequest() {
        Platform.runLater(() ->
                connectionSceneController.addPlayerToGameRequest());
    }

    @Override
    public void loadResponse() {
        // Wait others to reconnect
    }

    @Override
    public void lobbyJoinResponse(Response response) {
        Platform.runLater(() ->
                startGameSceneController.onLobbyJoinResponse(response));
    }

    @Override
    public void numberOfPlayersRequest(Response response) {
        // Nothing to do
    }

    @Override
    public void notYourTurn(String turnOwner) {
        Platform.runLater(() ->
                gameSceneController.notYourTurn(turnOwner));
    }

    @Override
    public void firstPlayerCommunication(String username, List<CommonGoal> cg) {

    }

    @Override
    public void gameStateUpdate() {
        if (gameSceneController == null) {
            if (lobbySceneController == null) { // Game reconnection
                Platform.runLater(connectionSceneController::onReconnectionResponse);
            } else { // Game Start
                Platform.runLater(lobbySceneController::onGameStart);
            }
        } else {
            Platform.runLater(gameSceneController::onStateUpdate);
        }
    }

    @Override
    public void displayActions(List<PossibleAction> possibleActions) {

    }

    @Override
    public void pickBoardCard() {

    }

    @Override
    public void joinGame() {

    }

    @Override
    public void createGame() {

    }

    @Override
    public void printLimbo() {

    }

    @Override
    public void reorderLimbo() {

    }

    @Override
    public void deleteLimbo() {

    }

    @Override
    public void chooseColumn() {

    }

    @Override
    public void printWinner(GameSerialized gameSerialized) {

    }

    @Override
    public void printEndGame(String message) {

    }

    @Override
    public void printScore() {

    }

    @Override
    public void gameStateRequest(String username, String token) {

    }

    @Override
    public void onPlayerDisconnection(String username) {

    }

    @Override
    public void onPlayerReconnection(String username) {

    }

    @Override
    public void playersWaitingUpdate(List<String> users) {
        if (lobbySceneController != null) {
            Platform.runLater(() ->
                    lobbySceneController.updateLobbyList());
        }
    }

    @Override
    public void chooseGameToJoin(List<ControllerGame> games) {
        Platform.runLater(() ->
                startGameSceneController.onJoinGameResponse(games));
    }

    @Override
    public void noGameAvailable() {
        Platform.runLater(() ->
                startGameSceneController.noGameAvailable());
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
