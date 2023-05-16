package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.network.message.ConnectionResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.css.PseudoClass;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import it.polimi.ingsw.utility.ServerAddressValidator;

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
    private Button RMIButton;
    @FXML
    private Button socketButton;
    @FXML
    private Button backButton;

    /*
    @FXML
    private ImageView connectSocketButton;
    @FXML
    private ImageView connectRmiButton;
    @FXML
    private ImageView backButton;

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
        socketButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onConnectionButtonClick(0));
        RMIButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onConnectionButtonClick(1));
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

        boolean isUsernameValid = !username.equals("") && !username.equals("Server") && !username.equals("User");

        boolean isAddressValid = ServerAddressValidator.isAddressValid(address);

        boolean isPortValid = ServerAddressValidator.isPortValid(portField.getText());

        usernameField.pseudoClassStateChanged(errorPseudo, !isUsernameValid);
        addressField.pseudoClassStateChanged(errorPseudo, !isAddressValid);
        portField.pseudoClassStateChanged(errorPseudo, !isPortValid);

        if (isUsernameValid && isAddressValid && isPortValid) {
            backButton.setDisable(true);
            socketButton.setDisable(true);
            RMIButton.setDisable(true);

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
            GuiManager.setLayout(mainPane.getScene(), "fxml/numberPlayersScene.fxml");
        } else {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, response.getMessage());

            guiManager.closeConnection();
            onBackButtonClick();
        }
    }

/*
    void onReconnectionResponse() {
        GameSceneController gameSceneController =
                GuiManager.setLayout(mainPane.getScene(), "fxml/gameScene.fxml");

        if (gameSceneController != null) {
            gameSceneController.setupGame(guiManager.getGameSerialized());
            gameSceneController.onStateUpdate();
        }
    }

 */

    /**
     * Handles an error occurrence
     *
     * @param error message of error
     */
    void onError(String error) {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, error);
    }
}
