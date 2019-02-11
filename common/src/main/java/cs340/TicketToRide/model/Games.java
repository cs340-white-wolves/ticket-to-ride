package cs340.TicketToRide.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs340.TicketToRide.utility.ID;

public class Games {
    private Set<Game> games;

    public Games() {
        games = new HashSet<>();
    }

    public boolean addGame(Game game) {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        if (games.contains(game)) {
            throw new RuntimeException("This game already exists");
        }

        return games.add(game);
    }

    public boolean removeGame(ID gameID) {
        Game game = getGameByID(gameID);
        if (game == null) {
            throw new RuntimeException("This game does not exist");
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

    public Set<Game> getGameSet() { return games; }

    public List<Game> getGameList() {
        List<Game> list = new ArrayList<Game>();
        list.addAll(games);
        return list;
    }

}
