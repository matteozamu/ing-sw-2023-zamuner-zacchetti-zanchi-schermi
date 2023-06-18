# Prova Finale di Ingegneria del Software - a.a. 2022-2023

[![codecov](https://codecov.io/gh/matteozamu/ing-sw-2023-zamuner-zacchetti-zanchi-schermi/branch/dev/graph/badge.svg?token=KBR43CAQFL)](https://codecov.io/gh/matteozamu/ing-sw-2023-zamuner-zacchetti-zanchi-schermi)

![alt text](https://www.craniocreations.it/storage/media/products/54/112/My_Shelfie_box_ITA-ENG.png)

L'obiettivo principale del progetto è implementare il gioco da tavolo MyShelfie utilizzando l'architettura Model View
Controller (MVC), secondo il paradigma di programmazione orientato agli oggetti. Il risultato finale rispetta
integralmente le regole del gioco e offre la possibilità di interagire con esso attraverso un'interfaccia a riga di
comando (CLI) o tramite un'accattivante grafica (GUI). Per quanto riguarda la comunicazione via rete, sono state
utilizzate sia le socket che RMI.

## Documentazione

Nella seguente documentazione sono inclusi i documenti sviluppati per la progettazione del gioco. Prima verranno
presentati gli elenchi dei diagrammi delle classi in UML, seguiti dalla documentazione del codice (JavaDoc).

### UML

Di seguito sono riportati diagrammi delle classi dell’architettura di rete dei client e del server

- [UML alto livello client server](*LINK GIT*)
- [UML dettagliato client server](*LINK GIT*)
- [UML protocollo comunicazione client server](*LINK GIT*)

### Peer Review

- [Peer review - Model](https://github.com/matteozamu/ing-sw-2023-zamuner-zacchetti-zanchi-schermi/blob/main/Peer%20review%20Model%20-%20GC5.md)
- [Peer review - Network ](https://github.com/matteozamu/ing-sw-2023-zamuner-zacchetti-zanchi-schermi/blob/main/Peer%20Review%20Network%20-%20GC5.md)

### JavaDoc

Il presente documento fornisce una descrizione dettagliata della maggior parte delle classi e dei metodi utilizzati nel
progetto, seguendo le convenzioni di documentazione di Java. È possibile consultare questa
documentazione [cliccando qui](*LINK GIT*)

### Librerie e Plugins

| Libreria/Plugin | Descrizione                                                                                 |
|-----------------|---------------------------------------------------------------------------------------------|
| __maven__       | Strumento di gestione per software basati su Java e build automation                        |
| __junit__       | Framework dedicato a Java per unit testing                                                  |
| __gson__        | Libreria per il supporto al parsing di file in formato json                                 |
| __JavaFx__      | Libreria grafica di Java                                                                    |
| __jacoco__      | Strumento di supporto al testing per evidenziare le linee di codice coperte dagli unit test |

### Jars

Per la consegna del progetto sono stati utilizzati i seguenti Jar, i quali consentono di avviare il gioco secondo le
funzionalità descritte nell'introduzione. Le funzionalità sviluppate in accordo con la specifica del progetto saranno
elencate nella prossima sezione, mentre i dettagli relativi all'esecuzione dei Jar saranno forniti nella sezione
denominata "Esecuzione dei Jar". La cartella che ospita il software del client e del server si trova all'indirizzo
seguente: [Jars](*LINK GIT*).

## Funzionalità

### Funzionalità Sviluppate

- Regole complete
- CLI
- GUI
- Socket
- RMI

### Funzionalità Aggiuntive Sviluppate

- Partite multiple
- Resilienza alle disconnessioni

## Esecuzione dei JAR

### Client

Il client può essere eseguito in due modalità: CLI e GUI; il giocatore può scegliere la modalità di gioco tramite un
comando apposito all'inizio dell'esecuzione del Jar.

#### CLI

Per eseguire in client in modalità testuale, è necessario eseguire il seguente comando da terminale:

```bash
java -jar client.jar cli
```

#### GUI

DA SCRIVERE

### Server

L'esecuzione del Server prevede la lettura di un file di configurazione di tipo Json, contenente costanti e impostazioni
di gioco; i valori eventualmente modificabili sono i seguenti:

```
"socketPort": 2727,
"RMIPort": 7272,
"maxPlayers": 4,
"minPlayers": 2,
"disconnectionTimer": 30000
```

#### Options

- `socketPort`: porta del server che usa le socket.
- `RMIPort`: porta del server che usa il servizio RMI.
- `maxPlayers`: numero massimo di giocatori che possono partecipare ad una partita.
- `minPlayers`: numero minimo di giocatori che possono partecipare ad una partita.
- `disconnectionTimer`: tempo in millisecondi che il server aspetta prima di considerare un giocatore definitivamente
  disconnesso dalla partita in corso.

L'esecuzione del server avviene attraverso il seguente comando, in cui si specifica il percorso del file di
configurazione:

```bash
java -jar server.jar [configFilePath] 
```

Se non specificato nessun percorso, il valore di default è _GameConstant.json_.

## Componenti del gruppo

- [__Matteo Zamuner__](https://github.com/matteozamu)
- [__Simone Zacchetti__](https://github.com/SimoneZacchetti)
- [__Federica Zanchi__](https://github.com/federicazanchi)
- [__Federico Schermi__](https://github.com/federicoschermi)