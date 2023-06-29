package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.utility.MessageBuilder;
import it.polimi.ingsw.utility.ServerAddressValidator;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class ConnectionSceneController represents the controller of the connection scene, the scene where the player can
 * choose the connection type and insert their username and the server address
 */

public class ConnectionSceneController {
    private final PseudoClass errorPseudo = PseudoClass.getPseudoClass("error");
    private GuiManager guiManager;

    @FXML
    private Pane mainPane;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;
    @FXML
    private ImageView connectSocketButton;
    @FXML
    private ImageView connectRmiButton;
    @FXML
    private ImageView backButton;

    /**
     * Initializes the scene
     */
    @FXML
    public void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setConnectionSceneController(this);

        bindEvents();
        setInputFormat();
    }

    /**
     * Binds click events
     */
    private void bindEvents() {
        connectSocketButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onConnectionButtonClick(0));
        connectRmiButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onConnectionButtonClick(1));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onBackButtonClick());
    }

    /**
     * Sets the input formats for the textfield
     */
    private void setInputFormat() {
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > ServerAddressValidator.MAX_ADDRESS_LENGTH) {
                addressField.setText(newValue.substring(0, ServerAddressValidator.MAX_ADDRESS_LENGTH));
            }
        });

        portField.setTextFormatter(new TextFormatter<String>(change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) {
                return change;
            }

            return null;
        }));

        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > ServerAddressValidator.MAX_PORT_LENGTH) {
                portField.setText(newValue.substring(0, ServerAddressValidator.MAX_PORT_LENGTH));
            }
        });
    }

    /**
     * Handles the back button click
     */
    private void onBackButtonClick() {
        connectSocketButton.setDisable(true);
        connectRmiButton.setDisable(true);
        backButton.setDisable(true);

        GuiManager.setLayout(mainPane.getScene(), "fxml/menuScene.fxml");
    }

    /**
     * Handles the click of connection button
     *
     * @param connection type of connection
     */
    private void onConnectionButtonClick(int connection) {
        final String username = usernameField.getText();
        final String address = addressField.getText();
        final String port = portField.getText();

        boolean isUsernameValid =
                !username.equals("") &&
                        !username.equals("Server") &&
                        !username.equals("server") &&
                        !username.equals("User") &&
                        !username.equals("user");

        boolean isAddressValid = ServerAddressValidator.isAddressValid(address);

        boolean isPortValid = ServerAddressValidator.isPortValid(portField.getText());

        usernameField.pseudoClassStateChanged(errorPseudo, !isUsernameValid);
        addressField.pseudoClassStateChanged(errorPseudo, !isAddressValid);
        portField.pseudoClassStateChanged(errorPseudo, !isPortValid);

        if (isUsernameValid && isAddressValid && isPortValid) {
            backButton.setDisable(true);
            connectSocketButton.setDisable(true);
            connectRmiButton.setDisable(true);

            try {
                GuiManager.getInstance().createConnection(connection, username, address, Integer.parseInt(port), GuiManager.getInstance());
            } catch (Exception e) {
                GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                        "Failed to establish a connection!");

                onBackButtonClick();
            }
        }
    }

    /**
     * Handles the connection response
     *
     * @param response connection response
     */
    void onConnectionResponse(ConnectionResponse response) {
        if (response.getStatus() == MessageStatus.OK) {
            GuiManager.setLayout(mainPane.getScene(), "fxml/startGameScene.fxml");
        } else {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, response.getMessage());

            guiManager.closeConnection();
            onBackButtonClick();
        }
    }

    /**
     * Requests to add a player to the game, or shows an error dialog if the request fails.
     */
    void addPlayerToGameRequest() {
        if (!guiManager.sendRequest(MessageBuilder.buildAddPlayerToGameMessage(guiManager.getClientToken(),
                guiManager.getUsername(), false))) {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);

            onBackButtonClick();
        }
    }

    /**
     * Handles a reconnection response, setting up the game scene on successful reconnection.
     */
    void onReconnectionResponse() {
        GameSceneController gameSceneController =
                GuiManager.setLayout(mainPane.getScene(), "fxml/gameScene.fxml");

        if (gameSceneController != null) {
            gameSceneController.setupGame(guiManager.getGameSerialized());
            gameSceneController.onStateUpdate();
        }
    }

    /**
     * Handles an error occurrence
     *
     * @param error message of error
     */
    void onError(String error) {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, error);
    }
}
