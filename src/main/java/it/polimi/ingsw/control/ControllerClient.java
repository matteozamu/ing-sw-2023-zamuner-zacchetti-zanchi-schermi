package it.polimi.ingsw.control;

import it.polimi.ingsw.message.LoginReply;
import it.polimi.ingsw.message.LoginRequest;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerClient implements ViewObserver, Observer {
    private final View view;

    private Client client;
    private String username;

    private final ExecutorService taskQueue;

    /**
     * controller constructor
     * @param view is the view to controll
     */
    public ControllerClient(View view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    /**
     * Create a new Socket Connection to the server with the updated info.
     * An error view is shown if connection cannot be established.
     *
     * @param serverInfo a map of server address and server port.
     */
    @Override
    public void onUpdateServerInfo(Map<String, String> serverInfo) {
        try {
            client = new SocketClient(serverInfo.get("address"), Integer.parseInt(serverInfo.get("port")));
            client.addObserver(this);
            client.readMessage(); // Starts an asynchronous reading from the server.
            client.enablePinger(true);
            taskQueue.execute(view::askUsername);
        } catch (IOException e) {
            taskQueue.execute(() -> view.showLoginResult(false, false, this.username));
        }
    }

    @Override
    public void update(Message message) {
        switch (message.getMessageType()){
            case LOGIN_REPLY:
                LoginReply loginReply = (LoginReply) message;
                taskQueue.execute(() -> view.showLoginResult(loginReply.isUsernameAccepted(), loginReply.isConnectionSuccessful(), this.username));
                break;
            default:
                break;
        }

    }

    @Override
    public void onUpdateUsername(String username) {
        this.username = username;
        client.sendMessage(new LoginRequest(this.username));
    }

    @Override
    public void onUpdateObjCard(List<Coordinate> coordinates) {

    }

    /**
     * Validates the given IPv4 address by using a regex.
     *
     * @param ip the string of the ip address to be validated
     * @return {@code true} if the ip is valid, {@code false} otherwise.
     */
    public static boolean isValidIpAddress(String ip) {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        return ip.matches(regex);
    }

    /**
     * Checks if the given port string is in the range of allowed ports.
     *
     * @param portStr the ports to be checked.
     * @return {@code true} if the port is valid, {@code false} otherwise.
     */
    public static boolean isValidPort(String portStr) {
        try {
            int port = Integer.parseInt(portStr);
            return port >= 1 && port <= 65535;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
