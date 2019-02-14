package cs340.TicketToRide.model.game;

import java.util.Objects;
import java.util.Set;

import cs340.TicketToRide.model.User;

public class Player {

    enum Color {
        red, green, blue, yellow, black
    }

    private User user;
    private int score;
    private int numTrains;
    private Set<TrainCard> trainCards;
    private Set<DestinationCard> destinationCards;
    private Color color;

    // todo: should the player have a set of routes? or route has player?

    public Player(User user) {
        setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null || !user.isValid()) {
            throw new IllegalArgumentException();
        }
        this.user = user;
    }

    public boolean isValid() {
        return user != null && user.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(user, player.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
