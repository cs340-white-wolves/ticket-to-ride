package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public interface IServerModel {
    User getUserByAuthToken(AuthToken token);
    boolean addGame(Game game) throws Exception;
    Game getGameByID(ID gameID);
    User getUserByUsername(Username username);
    void registerUser(User user, AuthToken token) throws Exception;
    void loginUser(User user, AuthToken token) throws Exception;
    void clear();
}
