package a340.tickettoride;

import a340.tickettoride.communication.ClientCommunicator;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.communication.IServerCommand;
import cs340.TicketToRide.communication.Response;
import cs340.TicketToRide.communication.ServerCommand;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class ServerProxy implements IServer {

    private static ServerProxy singleton;
    private ServerProxy() {
    }

    public static ServerProxy getInstance() {
        if (singleton == null) {
            singleton = new ServerProxy();
        }

        return singleton;
    }

    public AuthToken login(Username username, Password password) throws Exception {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }
        IServerCommand command = new ServerCommand("login",
                new Class<?>[]{Username.class, Password.class}, new Object[]{username, password});

        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Response response = communicator.sendCommand(command);
        Object object = response.getResultObject();
        if (object instanceof Exception) {
            throw (Exception)object;
        }

        return (AuthToken)object;
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
