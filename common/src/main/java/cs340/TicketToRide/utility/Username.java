package cs340.TicketToRide.utility;

public class Username {

    private String username;

    public Username(String username) {
        setUsername(username);
    }

    private void setUsername(String username) {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

    public String toString() {
        return username;
    }

    public boolean isValid() {
        return this.username != null && !this.username.equals("");
    }
}
