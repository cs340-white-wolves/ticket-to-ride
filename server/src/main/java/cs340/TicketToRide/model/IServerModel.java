package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public interface IServerModel {
    User getUserByAuthToken(AuthToken token);
    void addGame(Game game);
    Game getGameByID(ID gameID);
    User getUserByUsername(Username username);
    void registerUser(User user, AuthToken token);
}
