package cs340.TicketToRide;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public interface IServer {
    LoginRegisterResponse login(Username username, Password password);
    LoginRegisterResponse register(Username username, Password password);
    Game createGame(AuthToken token, int numPlayers);
    Game joinGame(AuthToken token, ID gameId);
    Games getAvailableGames(AuthToken token);
    /*
        SendChat
        getDestCards? they get automatically
        Return dest cards
     */
}
