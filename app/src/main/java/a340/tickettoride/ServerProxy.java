package a340.tickettoride;

import android.util.Log;

import a340.tickettoride.communication.ClientCommunicator;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.communication.ICommand;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.communication.Response;
import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
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

    public LoginRegisterResponse login(Username username, Password password) {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }
        ICommand command = new Command(
                "login",
                new String[]{Username.class.getName(), Password.class.getName()},
                new Object[]{username, password}
        );
        Response response = communicator.sendCommand(command);
        if (response.isError()) {
            throw response.getException();
        }
        Object resultObject = response.getResultObject();
        return (LoginRegisterResponse)resultObject;
    }

    public LoginRegisterResponse register(Username username, Password password) {
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

        if (response.isError()) {
            throw response.getException();
        }

        Object resultObject = response.getResultObject();
        return (LoginRegisterResponse)resultObject;
    }

    public Game createGame(AuthToken token, int numPlayers) {
        if (token == null || !token.isValid() || numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command(
                "createGame",
                new String[]{AuthToken.class.getName(), int.class.getName()},
                new Object[]{token, numPlayers}
        );

        Response response = communicator.sendCommand(command);
        if (response.isError()) {
            throw response.getException();
        }

        Object resultObject = response.getResultObject();
        return (Game) resultObject;
    }

    public Game joinGame(AuthToken token, ID gameId) {
        if (token == null || gameId == null || !token.isValid() || !gameId.isValid()) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command(
                "joinGame",
                new String[]{AuthToken.class.getName(), ID.class.getName()},
                new Object[]{token, gameId}
        );


        Response response = communicator.sendCommand(command);
        if (response.isError()) {
            throw response.getException();
        }

        Object resultObject = response.getResultObject();
        return (Game) resultObject;
    }

    @Override
    public Games getAvailableGames(AuthToken token) {
        if (token == null || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        ICommand command = new Command(
                "getAvailableGames",
                new String[]{AuthToken.class.getName()},
                new Object[]{token}
        );


        Response response = communicator.sendCommand(command);

        if (response.isError()) {
            throw response.getException();
        }

        Object resultObject = response.getResultObject();

        return (Games) resultObject;
    }

    @Override
    public void sendChat(AuthToken token, ID gameId, Message message) {
        ICommand command = new Command(
                "sendChat",
                new String[]{AuthToken.class.getName(), ID.class.getName(), Message.class.getName()},
                new Object[]{token, gameId, message}
        );

        communicator.sendCommand(command);
    }

    @Override
    public Commands getQueuedCommands(AuthToken token, ID playerId, ID gameId, int index) {
        ICommand command = new Command(
                "getQueuedCommands",
                new String[]{AuthToken.class.getName(), ID.class.getName(), ID.class.getName(), int.class.getName()},
                new Object[]{token, playerId, gameId, index}
        );
        Response response = communicator.sendCommand(command);
        Object resultObject = response.getResultObject();
        return (Commands)resultObject;
    }

    @Override
    public void discardDestCards(DestinationCards cards, AuthToken token, ID gameId, ID playerId) {
        ICommand command = new Command(
                "discardDestCards",
                new String[]{cards.getClass().getName(), AuthToken.class.getName(),
                        ID.class.getName(), ID.class.getName()},
                new Object[]{cards, token, gameId, playerId}
        );
        communicator.sendCommand(command);
    }

    @Override
    public void claimRoute(Route route, AuthToken token, ID gameID, ID playerId) {
        ICommand command = new Command(
                "claimRoute",
                new String[]{Route.class.getName(), AuthToken.class.getName(),
                        ID.class.getName(), ID.class.getName()},
                new Object[]{route, token, gameID, playerId}
        );
        communicator.sendCommand(command);
    }

    @Override
    public void drawTrainCard(TrainCard card, AuthToken token, ID gameId, ID playerId) {
        ICommand command = new Command(
                "drawTrainCard",
                new String[]{TrainCard.class.getName(), AuthToken.class.getName(),
                        ID.class.getName(), ID.class.getName()},
                new Object[]{card, token, gameId, playerId}
        );
        communicator.sendCommand(command);
    }

    @Override
    public void drawDestCards(AuthToken token, ID gameId, ID playerId) {
        ICommand command = new Command(
                "drawDestCards",
                new String[]{AuthToken.class.getName(), ID.class.getName(), ID.class.getName()},
                new Object[]{token, gameId, playerId}
        );
        communicator.sendCommand(command);
    }

}
