package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public class Game {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 5;

    private int targetNumPlayers;
    private Set<Player> players;
    private ID gameID;
    private Username creator;

    public Game(int targetNumPlayers, Username creator) {
        setTargetNumPlayers(targetNumPlayers);
        players = new HashSet<>();
        gameID = ID.generateID();
        setCreator(creator);
    }

    public boolean addPlayer(Player player) {
        if (player == null || !player.isValid()) {
            throw new IllegalArgumentException();
        }

        if (players.contains(player)) {
            throw new RuntimeException("This player is already in the game");
        }

        if (hasTargetNumPlayers()) {
            throw new RuntimeException("This game already reached its max number of players");
        }

        return players.add(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public boolean isValid() {
        if (targetNumPlayers < MIN_PLAYERS || targetNumPlayers > MAX_PLAYERS) {
            return false;
        }

        if (players == null || gameID == null || !gameID.isValid()) {
            return false;
        }

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

    public int getNumCurrentPlayers() {
        return players.size();
    }

    public void setTargetNumPlayers(int targetNumPlayers) {
        if (targetNumPlayers < MIN_PLAYERS || targetNumPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        this.targetNumPlayers = targetNumPlayers;
    }

    public Username getCreator() {
        return creator;
    }

    public void setCreator(Username creator) {
        if (creator == null || !creator.isValid()) {
            throw new IllegalArgumentException();
        }
        this.creator = creator;
    }

}
