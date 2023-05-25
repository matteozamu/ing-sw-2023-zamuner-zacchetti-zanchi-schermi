# Peer-Review 1: UML

Matteo Zamuner, Simone Zacchetti, Federica Zanchi, Federico Schermi

Gruppo 15

Valutazione del diagramma UML delle classi del gruppo 5.

## Lati positivi

In seguito all'analisi del UML del model del progetto, si denota un'ottima suddivisione delle classi che permette una semplice ed efficace gestione della partita. Ogni classe è stata rappresentata nel file UML insieme alle sue relazioni con le altre classi, mostrando quindi in modo preciso come le classi si interconnettono tra loro. Nel complesso riteniamo che abbiate svolto un ottimo lavoro e che siate sulla giusta direzione per la corretta realizzazione del progetto.

## Lati negativi

Il modello non presenta particolari criticità, tuttavia possiamo proporre un paio di miglioramenti. In primo luogo, da quanto si evince analizzando l'UML, non sembra ci sia la possibilità di scegliere l'ordine con cui inserire le tessere oggetto nella "Shelf" prelevate dalla "Board". Pertanto, possiamo consigliare di selezionare tutte le carte da inserire nella propria libreria e solo successivamente scegliere la sequenza di inserimento, in modo da avere un comportamente più affine al game originale.

Inoltre, da quanto emerge dal diagramma delle classi del modello, il game non contiene tutte le carte obiettivo personale e comune istanziate all'inizio della partita, come invece avviene nel game fisico.

## Confronto tra le architetture

Analizzando il nostro modello, notiamo una similarità di pensiero, ciò significa che stiamo procedendo nella stessa direzione per la realizzazione del progetto. Ad ogni modo, abbiamo trovato alcune idee da cui potremmo prendere spunto per migliorare la nostra implementazione tra cui la gestione delle carte disponibili in una classe apposita "TileBag" e la possibilità di avere una cella della "Board" riempita con un valore fittizio per indicare l'assenza di carte al suo interno.

Infine, confrontando i nostri UML, identifichiamo una differenza data dalla presenza di una classe "CartaObiettivoPersonale" che rappresenta la carta fisica del game. Ad essa viene poi associata una istanza di "ObiettivoPersonale" che contiene le caratteristiche dei vari obiettivi di quella specifica carta.
