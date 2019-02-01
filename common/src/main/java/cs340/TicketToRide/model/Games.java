package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.utility.ID;

public class Games {
    private Set<Game> games;

    public Games() {
        games = new HashSet<>();
    }

    public void addGame(Game game) {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        if (games.contains(game)) {
            // todo: throw exception?
            return;
        }

        games.add(game);
    }

    public Game getGameByID(ID gameID) {
        for (Game game : games) {
            if (game.getGameID().equals(gameID)) {
                return game;
            }
        }

        // todo: exception?
        return null;
    }

}
