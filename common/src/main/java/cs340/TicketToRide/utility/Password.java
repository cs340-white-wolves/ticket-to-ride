package cs340.TicketToRide.utility;

public class Password {

    private String password;

    public Password(String password) {
        setPassword(password);
    }

    private void setPassword(String password) {
        if (password == null || password.equals("")) {
            throw new IllegalArgumentException();
        }
        this.password = password;
    }

    public String toString() {
        return password;
    }

    public boolean isValid() {
        return this.password != null && !this.password.equals("");
    }
}
