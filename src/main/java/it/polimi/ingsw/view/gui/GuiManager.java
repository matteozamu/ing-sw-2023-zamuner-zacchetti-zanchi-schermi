package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.PossibleAction;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.GameSerialized;
import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.network.client.DisconnectionListener;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.network.message.Response;
import it.polimi.ingsw.utility.MessageBuilder;
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

/**
 * Class GuiManager represents the manager of the GUI
 */

public class GuiManager extends ClientGameManager implements DisconnectionListener {
    private static GuiManager instance = null;

    private ConnectionSceneController connectionSceneController;

    private GameSceneController gameSceneController;

    private LobbySceneController lobbySceneController;

    //private NumberPlayersController numberPlayersController;

    private StartGameSceneController startGameSceneController;

    //private JoinGameSceneController joinGameSceneController;

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


    /**
     * Sets the connection scene controller
     *
     * @param connectionSceneController connection scene controller
     */
    void setConnectionSceneController(ConnectionSceneController connectionSceneController) {
        this.connectionSceneController = connectionSceneController;
    }

    /**
     * Sets the game scene controller
     *
     * @param gameSceneController game scene controller
     */
    void setGameSceneController(GameSceneController gameSceneController) {
        this.gameSceneController = gameSceneController;
    }

    /**
     * Sets the lobby scene controller
     *
     * @param lobbySceneController lobby scene controller
     */
    void setLobbySceneController(LobbySceneController lobbySceneController) {
        this.lobbySceneController = lobbySceneController;
    }

//    /** Sets the number players controller
//     *
//     * @param numberPlayersController number players controller
//     */
//    void setNumberPlayersController(NumberPlayersController numberPlayersController) {
//        this.numberPlayersController = numberPlayersController;
//    }

    /**
     * Sets the start game scene controller
     *
     * @param startGameSceneController start game scene controller
     */
    void setStartGameSceneController(StartGameSceneController startGameSceneController) {
        this.startGameSceneController = startGameSceneController;
    }

//    /** Sets the join game scene controller
//     *
//     * @param joinGameSceneController join game scene controller
//     */
//    void setJoinGameSceneController(JoinGameSceneController joinGameSceneController) {
//        this.joinGameSceneController = joinGameSceneController;
//    }

    /**
     * The server sends the response to the connection request to the client
     *
     * @param response response to the connection request
     */
    @Override
    public void connectionResponse(ConnectionResponse response) {
        Platform.runLater(() ->
                connectionSceneController.onConnectionResponse(response));
    }

    /**
     * The client sends the request of adding a player to the server
     */
    @Override
    public void addPlayerToGameRequest() {
        Platform.runLater(() ->
                connectionSceneController.addPlayerToGameRequest());
    }

    /**
     * Loads a response
     */
    @Override
    public void loadResponse() {
        // Wait others to reconnect
    }

    /**
     * The server sends the response to request of joining the lobby to the client
     *
     * @param response response to the request
     */
    @Override
    public void lobbyJoinResponse(Response response) {
//        Platform.runLater(() ->
//                startGameSceneController.onLobbyJoinResponse(response));
    }

    /**
     * The server sends the response to request of the number of players to the client
     *
     * @param response response to the request
     */
    @Override
    public void numberOfPlayersRequest(Response response) {
        Platform.runLater(() ->
                startGameSceneController.numberOfPlayerRequest(response));
    }

    /**
     * Forbids other players to play the turn except turnOwner
     *
     * @param turnOwner username of the player who has to play
     */
    @Override
    public void notYourTurn(String turnOwner) {
        Platform.runLater(() ->
                gameSceneController.notYourTurn(turnOwner));
    }

    /**
     * The server sends the response to request of the number of players to the client
     *
     * @param username    username of the player who has to play
     * @param commonGoals list of common goals
     */
    @Override
    public void firstPlayerCommunication(String username, List<CommonGoal> commonGoals) {
        Platform.runLater(lobbySceneController::onGameStart);

        Platform.runLater(() ->
                gameSceneController.setCommonGoalCards(commonGoals));
    }

    /**
     * Updates the game state - reconnection or start
     */
    @Override
    public void gameStateUpdate() {
        if (gameSceneController == null) {
            if (lobbySceneController == null) { // Game reconnection
                Platform.runLater(connectionSceneController::onReconnectionResponse);
            }
            //else { // Game Start
            //Platform.runLater(lobbySceneController::onGameStart);
            //}
        } else {
            Platform.runLater(gameSceneController::onStateUpdate);
        }
    }

    /**
     * Displays the possible actions
     *
     * @param possibleActions list of possible actions
     */
    @Override
    public void displayActions(List<PossibleAction> possibleActions) {
        if (gameSceneController != null) {
            Platform.runLater(() ->
                    gameSceneController.displayAction(possibleActions)
            );
        }
    }

    @Override
    public void joinGame() {

    }

    /**
     * Creates a new game
     */
    @Override
    public void createGame() {

    }

    /**
     * Picks an object card from the board
     */
    @Override
    public void pickBoardCard() {

    }

    /**
     * Prints the limbo
     */
    @Override
    public void printLimbo() {
        Platform.runLater(() ->
                gameSceneController.setLimbo(getGameSerialized())
        );
    }

    /**
     * Reorders the limbo
     */
    @Override
    public void reorderLimbo() {

    }

    /**
     * Deletes the limbo
     */
    @Override
    public void deleteLimbo() {
        Platform.runLater(() ->
                gameSceneController.deleteLimbo()
        );
    }

    /**
     * Picks a column from the shelf
     */
    @Override
    public void chooseColumn() {

    }

    @Override
    public void showPersonalGoal() {

    }

    @Override
    public void cancelAction() {

    }

    @Override
    public void showShelf() {

    }

    /**
     * Prints the winner of the game
     *
     * @param gameSerialized game serialized
     */
    @Override
    public void printWinner(GameSerialized gameSerialized) {

    }

    /**
     * Prints the end of the game
     *
     * @param message message to print
     */
    @Override
    public void printEndGame(String message) {

    }

    /**
     * Prints the score of the players
     */
    @Override
    public void printScore() {

    }

    @Override
    public void gameStateRequest(String username, String token) {

    }

    /**
     * Handles the disconnection of a player
     *
     * @param username username of the player who disconnected
     */
    @Override
    public void onPlayerDisconnection(String username) {
        if (gameSceneController != null) {
            Platform.runLater(() -> gameSceneController.onPlayerDisconnection(username));
        }
    }

    /**
     * Handles the reconnection of a player
     */
    @Override
    public void onPlayerReconnection(String message) {
        if (gameSceneController != null) {
            Platform.runLater(() -> gameSceneController.onPlayerReconnection(message));
        }
    }

    /**
     * Updates the lobby list
     *
     * @param users list of players that are waiting
     */
    @Override
    public void playersWaitingUpdate(List<String> users) {
        if (lobbySceneController != null) {
            Platform.runLater(() ->
                    lobbySceneController.updateLobbyList());
        }
    }

    /**
     * Handles the request of joining an existing game
     *
     * @param games list of existing games
     */
    @Override
    public void chooseGameToJoin(List<ControllerGame> games) {
        Platform.runLater(() ->
                startGameSceneController.onJoinGameResponse(games));
    }

    /**
     * Handles the situation of no existing games
     */
    @Override
    public void noGameAvailable() {
        Platform.runLater(() ->
                startGameSceneController.noGameAvailable());
    }

    /**
     * Handles error cases based on the scene
     *
     * @param error error message
     */
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

    /**
     * Handles the disconnection of the client
     */
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
}
