package it.polimi.ingsw.model;

// Quattro gruppi separati formati ciascuno da quattro tessere adiacenti dello stesso tipo.
// Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.

public class CommonCardType5 extends CommonGoal {

    @Override
    public boolean checkGoal(Shelf shelf) {
        return false;
    }
  }
