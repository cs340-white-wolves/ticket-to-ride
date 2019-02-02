package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.utility.ID;

public class Games {
    private Set<Game> games;

    public Games() {
        games = new HashSet<>();
    }

    public void addGame(Game game) throws Exception {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        if (games.contains(game)) {
            throw new Exception("This game already exists");
        }

        games.add(game);
    }

    public Game getGameByID(ID gameID) {
        for (Game game : games) {
            if (game.getGameID().equals(gameID)) {
                return game;
            }
        }

        return null;
    }

    public void clear() {
        games.clear();
    }

}
