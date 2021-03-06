package cs340.TicketToRide.model;

import java.util.Objects;

import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class User {

    private ID userID;
    private Username username;
    private Password password;

    public User(Username username, Password password) {
        setUsername(username);
        setPassword(password);
        userID = ID.generateID();
    }

    public boolean isValid() {
        return (userID != null && username != null && password != null
        && userID.isValid() && username.isValid() && password.isValid());
    }

    public ID getUserID() {
        return this.userID;
    }

    public void setUserID(ID userID) {
        if (userID == null || !userID.isValid()) {
            throw new IllegalArgumentException();
        }
        this.userID = userID;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        if (username == null || !username.isValid()) {
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        if (password == null || !password.isValid()) {
            throw new IllegalArgumentException();
        }
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userID, user.userID) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, username);
    }
}
