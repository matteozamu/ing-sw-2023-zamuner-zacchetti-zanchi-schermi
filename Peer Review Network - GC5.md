# Peer-Review 2: Network

Matteo Zamuner, Simone Zacchetti, Federica Zanchi, Federico Schermi

Group 15

Evaluation of the group 5 network communication protocol.

## Introduction

The group demonstrates a very positive and advanced implementation of the network side of the game.
After analyzing the UML and the provided documentation, we observed that in general, the structure of the client and the server is quite similar to ours, so we have very few criticisms to point out.

## Connection to the server

The server side is already oriented to support both socket and RMI connections, showing that the group is aiming to adopt all the requested requirements.
The only thing we would like to suggest is to make sure that the ports used by the servers are not used by any other application.

## Notifying messages

Based on the observation of your implementation schemes, it seems like the group has been strictly following the model of operation explained during the lessons. The communication based on a TCP-IP socket seems very functional, also because of the possibility of the server to send unsolicited messages.
Regarding the RMI part, we think that it is already quite complete based on the requirements of this kind of implementation.
A difference we noticed from our implementation is that this group differentiates two types of messages, while we have only one abstract class, which is extended by all the specific messages. Give or take, the choice of the message types is similar to ours: you went further by adding a Payload record.
The chat functionality is already implemented, which is an advanced requirement.
It appears that you have 2 distinct controllers, one for the lobby and one for the game, whereas we just have one that includes both functionalities.

## Conclusion

Many parts of your implementation are not mentioned here: this means that we have nothing to add to your work.
Overall, as we said before, we think that you are going in the right direction and that your game will turn out very strong.
