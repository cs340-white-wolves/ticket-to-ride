package a340.tickettoride;

import android.util.Log;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.task.CreateGameTask;
import a340.tickettoride.task.JoinGameTask;
import a340.tickettoride.task.LoginTask;
import a340.tickettoride.task.RegisterTask;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

/**
 * This class will call the server proxy but it will also update the client model
 */
public class ServiceFacade {
    private static ServiceFacade singleton;

    private ServiceFacade() {
    }

    public static ServiceFacade getInstance() {
        if (singleton == null) {
            singleton = new ServiceFacade();
        }
        return singleton;
    }

    public void login(Username username, Password password) {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        LoginTask task = new LoginTask(username, password);
        task.execute();
    }

    public void register(Username username, Password password) {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        RegisterTask task = new RegisterTask(username, password);
        task.execute();
    }

    public void createGame(int numPlayers) {
        if (numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }
        CreateGameTask task = new CreateGameTask(numPlayers);
        task.execute();
    }

    public void joinGame(ID gameID) {
        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }
        JoinGameTask task = new JoinGameTask(gameID);
        task.execute();
    }

    public void setupGame() {

    }

    public void sendChatMessage(String message) {
        Log.i("ServiceFacade", "Got Chat: " + message);
        IClientModel model = ClientModel.getInstance();
        model.onChatMessageReceived(new ChatMessage("test", message));
        // TODO: actually send the message.
    }
}
