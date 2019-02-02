import cs340.TicketToRide.IServer;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class ServerProxy implements IServer {
    public AuthToken login(Username username, Password password) throws Exception {
        return null;
    }
    public AuthToken register(Username username, Password password) throws Exception {
        return null;
    }
    public Game createGame(AuthToken token, int numPlayers) throws Exception {
        return null;
    }
    public boolean joinGame(AuthToken token, ID gameId) throws Exception {
        return false;
    }
}
