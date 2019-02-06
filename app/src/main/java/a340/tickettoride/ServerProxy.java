package a340.tickettoride;

import a340.tickettoride.communication.ClientCommunicator;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.communication.ICommand;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.communication.Response;
import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class ServerProxy implements IServer {

    private static ServerProxy singleton;
    private ClientCommunicator communicator = ClientCommunicator.getInstance();
    private ServerProxy() {
    }

    public static ServerProxy getInstance() {
        if (singleton == null) {
            singleton = new ServerProxy();
        }

        return singleton;
    }

    public LoginRegisterResponse login(Username username, Password password) throws Exception {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }
        ICommand command = new Command("login",
                new Class<?>[]{Username.class, Password.class}, new Object[]{username, password});

        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        if (resultObject instanceof Exception) {
            throw (Exception)resultObject;
        }

        return (LoginRegisterResponse)resultObject;
    }

    public LoginRegisterResponse register(Username username, Password password) throws Exception {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command("register",
                new Class<?>[]{Username.class, Password.class}, new Object[]{username, password});
        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        if (resultObject instanceof Exception) {
            throw (Exception)resultObject;
        }

        return (LoginRegisterResponse)resultObject;
    }

    public Game createGame(AuthToken token, int numPlayers) throws Exception {
        if (token == null || !token.isValid() || numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command("createGame",
                new Class<?>[]{AuthToken.class, int.class}, new Object[]{token, numPlayers});
        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        if (resultObject instanceof Exception) {
            throw (Exception)resultObject;
        }

        return (Game)resultObject;
    }

    public boolean joinGame(AuthToken token, ID gameId) throws Exception {
        if (token == null || gameId == null || !token.isValid() || !gameId.isValid()) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command("joinGame",
                new Class<?>[]{AuthToken.class, ID.class}, new Object[]{token, gameId});
        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        if (resultObject instanceof Exception) {
            throw (Exception)resultObject;
        }

        return (Boolean)resultObject;

        // todo: Check if not instance of boolean?
    }

    @Override
    public Games getGameList() {
        return null;
    }
}
