package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.utility.ID;

public class Game {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 5;
    private int targetNumPlayers;
    private Set<Player> players;
    private ID gameID;

    public Game(int targetNumPlayers) {
        setTargetNumPlayers(targetNumPlayers);
        players = new HashSet<>();
        gameID = ID.generateID();
    }

    public void addPlayer(Player player) {
        if (player == null || !player.isValid()) {
            throw new IllegalArgumentException();
        }

        if (players.contains(player)) {
            // todo: throw exception?
            return;
        }

        if (hasTargetNumPlayers()) {
            // todo: throw exception?
            return;
        }

        players.add(player);
    }

    // todo: return later
    public boolean isValid() {
        return true;
    }

    public boolean hasTargetNumPlayers() {
        return players.size() == targetNumPlayers;
    }

    public ID getGameID() {
        return gameID;
    }

    public int getTargetNumPlayers() {
        return targetNumPlayers;
    }

    public void setTargetNumPlayers(int targetNumPlayers) {
        if (targetNumPlayers < MIN_PLAYERS || targetNumPlayers > MAX_PLAYERS) {
            // todo: throwException?
            return;
        }

        this.targetNumPlayers = targetNumPlayers;
    }
}
