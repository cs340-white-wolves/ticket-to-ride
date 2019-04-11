package cs340.TicketToRide.flatfile;

import java.util.Set;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.db.IUserDao;

public class UserDao implements IUserDao {
    @Override
    public void saveUsers(Set<User> users) {

    }

    @Override
    public Set<User> loadUsers() {
        return null;
    }

    @Override
    public void saveTokens(AuthManager authManager) {

    }

    @Override
    public AuthManager loadTokens() {
        return null;
    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void clearAll() {

    }
}
