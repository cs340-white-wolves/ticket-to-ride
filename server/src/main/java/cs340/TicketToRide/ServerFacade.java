package cs340.TicketToRide;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.GameFullException;
import cs340.TicketToRide.exception.NotUniqueException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.service.CreateGameService;
import cs340.TicketToRide.service.GamesService;
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

    public LoginRegisterResponse login(Username username, Password password) throws AuthenticationException {
        return new LoginService().login(username, password);
    }
    public LoginRegisterResponse register(Username username, Password password) throws NotUniqueException {
        return new RegisterService().register(username, password);
    }
    public Game createGame(AuthToken token, int numPlayers) throws AuthenticationException {
        return new CreateGameService().createGame(token, numPlayers);
    }
    public Game joinGame(AuthToken token, ID gameId) throws GameFullException, AuthenticationException {
        return new JoinGameService().joinGame(token, gameId);
    }

    public Games getGameList() {
        return new GamesService().getAvailableGames();
    }

}
