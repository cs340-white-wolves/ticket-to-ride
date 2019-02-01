package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private Set<Player> players;

    public Game() {
        players = new HashSet<>();
    }

    public void addPlayer(Player player) {
        if (player == null || !player.isValid()) {
            throw new IllegalArgumentException();
        }

        if (players.contains(player)) {
            // todo: throw exception?
            return;
        }
        players.add(player);
    }

    // todo: return later
    public boolean isValid() {
        return true;
    }
}
