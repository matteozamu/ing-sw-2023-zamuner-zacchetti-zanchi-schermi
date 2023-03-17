package it.polimi.ingsw.model;

import java.util.List;
import java.util.Random;

public enum ObjectCardType {
    gatto,
    libro,
    gioco,
    cornice,
    trofeo,
    pianta;

    private static final List<ObjectCardType> VALUES =
            List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ObjectCardType randomObjectCardType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}