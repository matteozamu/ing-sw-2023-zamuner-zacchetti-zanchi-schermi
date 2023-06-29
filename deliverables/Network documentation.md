# 1 Network

The pattern we chose to develop the communication is observer-observable. We extended the pattern by introducing
messages between client and server.

## 1.1 Messages

Several classes were introduced in order to represent the messages exchanged between client and server. All the messages
extend the `Message` abstract class and each one of them identifies a specific instance of communication. The messages
we created so far are the following:

* `ConnectionRequest`: It is sent by the client to ask a connection with the server.
* `ConnectionResponse`: It returns the status of the connection to the client. In case of success, a token is also sent,
  which allows the server to identify each client.
* `CreateGame`: It is sent by the client to ask the server to create a new game.
* `DeleteLimboRequest`: Message used to put the selected object cards back on the board.
* `DisconnectionMessage`: Message used to inform all the clients that a player disconnected.
* `EndGameMessage`: Message class to inform the clients that the game is ended.
* `GameStartMessage`: Message class to tell the clients that the game has started.
* `GameStateRequest`: Message sent from the server with the updated model.
* `GameStateResponse`: Message class to inform the clients about the model changes.
* `JoinGameRequest`: Message used to request to join an existing game.
* `ListGameRequest`: Message used to request the list of available games.
* `ListGameResponse`: Message that contains the list of available games.
* `LoadShelfRequest`: Message sent by the client to load the shelf of the player.
* `LobbyMessage`: It is used by the client to join the lobby of the game.
* `LobbyPlayersResponse`: It is sent by the server and contains the usernames of the players in the lobby.
* `NumberOfPlayerMessage`: It is sent by the server to ask the number of players joining the game.
* `ObjectCardRequest`: The client sends this message with the chosen object card in order to verify its availability
  according to the game’s rules.
* `ReconnectionMessage`: Message used to handle a reconnection to the server. It is the response of
  a `ReconnectionRequest`.
* `ReconnectionRequest`: Message used to ask for a reconnection to the server.
* `ReorderLimboRequest`: Message used to reorder the selected object cards.
* `Response`: Generic response to a request.

## 1.2 Client

`ClientMain` is the starting point for the client: it chooses a CLI implementation or a GUI implementation, based on the
user’s will. If the CLI is the picked option, a `Cli` object is created. This class extends the `ClientGameManager`
class that updates the view by calling the right methods based on the received messages. It also implements two further
interfaces: `ClientGameManagerListener` and `ClientUpdateListener`. `ClientGameManagerListener` is used to let the game
progress via user inputs, and its methods are called whenever a message is received from the server,
whereas `ClientUpdateListener` is characterized by its `onUpdate` method, which notifies the implementer that a message
has been received. In order to notify the client of messages from the server, we have a `ClientUpdater` class: it runs a
process that awaits messages and notifies the client. `ClientRMI` and `ClientSocket` extend the `Client` class: they
represent the client for each network implementation. More specifically, `ClientRMI` implements
the `RMIClientConnection` interface.

## 1.3 Server

Our server is represented by the `Server` class that contains all the necessary methods for the game’s implementation