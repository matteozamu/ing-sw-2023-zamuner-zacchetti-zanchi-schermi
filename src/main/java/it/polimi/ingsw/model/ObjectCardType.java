package it.polimi.ingsw.model;

import java.util.List;
import java.util.Random;

public enum ObjectCardType {
    gatto("Gatto"),
    libro("Libro"),
    gioco("Gioco"),
    cornice("Cornice"),
    trofeo("Trofeo"),
    pianta("Pianta");

    private final String text;

    ObjectCardType(final String text) {
        this.text = text;
    }

    private static final List<ObjectCardType> VALUES =
            List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ObjectCardType randomObjectCardType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    @Override
    public String toString() {
        return text;
    }
}