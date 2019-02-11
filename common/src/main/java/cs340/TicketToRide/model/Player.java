package cs340.TicketToRide.model;

import java.util.Objects;

public class Player {

    private User user;

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
