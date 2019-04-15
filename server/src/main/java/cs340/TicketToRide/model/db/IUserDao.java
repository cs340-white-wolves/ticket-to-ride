package cs340.TicketToRide.model.db;

import java.util.Set;

import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.Users;

public interface IUserDao extends IDao {
    void saveUsers(Users users);
    Users loadUsers();
    void saveTokens(AuthManager authManager);
    AuthManager loadTokens();
}
