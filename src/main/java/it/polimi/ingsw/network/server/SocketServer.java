package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {
    private final Server server;
    private final int port;
    private ServerSocket serverSocket;

    /**
     * Constructs the server with main server and port
     *
     * @param server instance of main server
     * @param port   port of socket server
     */
    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Starts the socket Server
     */
    void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            start();
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                new SocketConnection(this, client);
            } catch (IOException e) {
                Server.LOGGER.warning(e.getMessage());
            }
        }
    }

    /**
     * handle the login of a client
     *
     * @param username   the username to log in
     * @param connection the client connection
     */
    void login(String username, Connection connection) {
        server.login(username, connection);
    }

    /**
     * method used to send a message received from the client to the server
     *
     * @param message is the message received from the client
     */
    void onMessage(Message message) {
        server.onMessage(message);
    }

    /**
     * method used to disconnect the client
     *
     * @param playerConnection is the player connection
     */
    void onDisconnect(Connection playerConnection) {
        server.onDisconnect(playerConnection);
    }
}
