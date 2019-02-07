package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;

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
}
