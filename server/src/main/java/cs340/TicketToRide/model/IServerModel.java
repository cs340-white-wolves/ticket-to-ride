package cs340.TicketToRide.model;

import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.NotUniqueException;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public interface IServerModel {
    User getUserByAuthToken(AuthToken token);
    boolean addGame(Game game);
    Game getGameByID(ID gameID);
    User getUserByUsername(Username username);
    void registerUser(User user, AuthToken token) throws NotUniqueException;
    void loginUser(User user, AuthToken token) throws AuthenticationException;
    void clear();
    void setupGame(Game game);
}
