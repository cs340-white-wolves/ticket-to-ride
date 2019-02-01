package cs340.TicketToRide;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public interface IServer {
    AuthToken login(Username username, Password password);
    AuthToken register(Username username, Password password);
    Game createGame(AuthToken token);
    boolean joinGame(AuthToken token, ID gameId);
}
