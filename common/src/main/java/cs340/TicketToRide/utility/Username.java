package cs340.TicketToRide.utility;

import java.util.Objects;

public class Username {

    private String username;

    public Username(String username) {
        setUsername(username);
    }

    private void setUsername(String username) {
        if (username == null || username.equals("") || username.contains(" ")) {
            throw new IllegalArgumentException("Invalid Username");
        }
        this.username = username;
    }

    public String toString() {
        return username;
    }

    public boolean isValid() {
        return this.username != null && !this.username.equals("") && !this.username.contains(" ");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Username u = (Username)o;
        return this.username.equals(u.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
