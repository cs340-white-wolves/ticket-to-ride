package a340.tickettoride;

import android.util.Log;

import a340.tickettoride.communication.ClientCommunicator;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.communication.ICommand;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.communication.Response;
import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.GameFullException;
import cs340.TicketToRide.exception.NotUniqueException;
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

    public LoginRegisterResponse login(Username username, Password password) throws AuthenticationException {
        Log.d("ServerProxy", "in login");
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command(
                "login",
                new String[]{Username.class.getName(), Password.class.getName()},
                new Object[]{username, password}
        );

        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();

        if (resultObject instanceof AuthenticationException) {
            throw (AuthenticationException) resultObject;
        }

        throwIfException(resultObject);
        return (LoginRegisterResponse)resultObject;
    }

    public LoginRegisterResponse register(Username username, Password password) throws NotUniqueException {
        Log.d("ServerProxy", "in register");
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command(
                "register",
                new String[]{Username.class.getName(), Password.class.getName()},
                new Object[]{username, password}
        );
        Log.d("ServerProxy", "later in register");

        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        if (resultObject instanceof NotUniqueException) {
            throw (NotUniqueException) resultObject;
        }

        throwIfException(resultObject);
        return (LoginRegisterResponse)resultObject;
    }

    public Game createGame(AuthToken token, int numPlayers) throws AuthenticationException {
        if (token == null || !token.isValid() || numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command(
                "createGame",
                new String[]{AuthToken.class.getName(), int.class.getName()},
                new Object[]{token, numPlayers}
        );

        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        if (resultObject instanceof AuthenticationException) {
            throw (AuthenticationException) resultObject;
        }

        throwIfException(resultObject);
        return (Game)resultObject;
    }

    public Game joinGame(AuthToken token, ID gameId) throws GameFullException, AuthenticationException {
        if (token == null || gameId == null || !token.isValid() || !gameId.isValid()) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command(
                "joinGame",
                new String[]{AuthToken.class.getName(), ID.class.getName()},
                new Object[]{token, gameId}
        );

        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        if (resultObject instanceof GameFullException) {
            throw (GameFullException) resultObject;
        }
        if (resultObject instanceof AuthenticationException) {
            throw (AuthenticationException) resultObject;
        }

        throwIfException(resultObject);
        return (Game) resultObject;
    }

    @Override
    public Games getGameList() {
        return null;
    }

    private void throwIfException(Object resultObject) {
        if (resultObject instanceof Exception) {
            throw new RuntimeException(((Exception)resultObject));
        }
    }
}
