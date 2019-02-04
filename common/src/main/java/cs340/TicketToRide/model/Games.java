package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.utility.ID;

public class Games {
    private Set<Game> games;

    public Games() {
        games = new HashSet<>();
    }

    public boolean addGame(Game game) throws Exception {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        if (games.contains(game)) {
            throw new Exception("This game already exists");
        }

        return games.add(game);
    }

    public boolean removeGame(ID gameID) throws Exception {
        Game game = getGameByID(gameID);
        if (game == null) {
            throw new Exception("This game does not exist");
        }

        return games.remove(game);
    }

    public Game getGameByID(ID gameID) {
        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        for (Game game : games) {
            if (game.getGameID().equals(gameID)) {
                return game;
            }
        }

        return null;
    }

    public void clear() {
        if (games == null) {
            return;
        }
        games.clear();
    }

}
