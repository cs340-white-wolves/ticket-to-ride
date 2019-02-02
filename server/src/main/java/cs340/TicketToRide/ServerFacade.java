package cs340.TicketToRide;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.service.CreateGameService;
import cs340.TicketToRide.service.JoinGameService;
import cs340.TicketToRide.service.LoginService;
import cs340.TicketToRide.service.RegisterService;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class ServerFacade implements IServer {

    private static ServerFacade singleton;

    private ServerFacade() {
    }

    public static ServerFacade getInstance() {
        if (singleton == null) {
            singleton = new ServerFacade();
        }
        return singleton;
    }

    public AuthToken login(Username username, Password password) throws Exception {
        return new LoginService().login(username, password);
    }
    public AuthToken register(Username username, Password password) throws Exception {
        return new RegisterService().register(username, password);
    }
    public Game createGame(AuthToken token, int numPlayers) throws Exception {
        return new CreateGameService().createGame(token, numPlayers);
    }
    public boolean joinGame(AuthToken token, ID gameId) throws Exception {
        return new JoinGameService().joinGame(token, gameId);
    }

}
