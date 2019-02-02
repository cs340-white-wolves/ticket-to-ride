package cs340.TicketToRide;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public interface IServer {
    AuthToken login(Username username, Password password) throws Exception;
    AuthToken register(Username username, Password password) throws Exception;
    Game createGame(AuthToken token, int numPlayers) throws Exception;
    boolean joinGame(AuthToken token, ID gameId) throws Exception;
}
