package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;

public class Users {
    private Set<User> users;

    public Users() {
        users = new HashSet<>();
    }

    public Users(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void clear() {
        users.clear();
    }

    public Set<User> getUsers() {
        return users;
    }
}
