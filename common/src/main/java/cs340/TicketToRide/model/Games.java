package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

public class Games {
    private Set<Game> games;

    public Games() {
        games = new HashSet<>();
    }

    public void addGame(Game game) {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        games.add(game);
    }
}
