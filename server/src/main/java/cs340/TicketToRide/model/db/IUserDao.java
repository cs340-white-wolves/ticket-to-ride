package cs340.TicketToRide.model.db;

import java.util.Set;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.User;

public interface IUserDao extends IDao {
    void saveUsers(Set<User> users);
    Set<User> loadUsers();
    void saveTokens(AuthManager authManager);
    AuthManager loadTokens();
}
