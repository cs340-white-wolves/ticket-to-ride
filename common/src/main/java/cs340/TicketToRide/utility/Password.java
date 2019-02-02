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

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Password p = (Password) o;
        return this.password.equals(p.password);
    }
}
