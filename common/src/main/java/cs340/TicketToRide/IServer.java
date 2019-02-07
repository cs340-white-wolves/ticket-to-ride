package cs340.TicketToRide;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.GameFullException;
import cs340.TicketToRide.exception.NotUniqueException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public interface IServer {
    LoginRegisterResponse login(Username username, Password password) throws AuthenticationException;
    LoginRegisterResponse register(Username username, Password password) throws NotUniqueException;
    Game createGame(AuthToken token, int numPlayers) throws AuthenticationException;
    Game joinGame(AuthToken token, ID gameId) throws GameFullException, AuthenticationException;
    Games getGameList();
}
