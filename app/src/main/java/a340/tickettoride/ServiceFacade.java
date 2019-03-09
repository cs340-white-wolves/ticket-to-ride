package a340.tickettoride;

import android.util.Log;

import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.task.ClaimRouteTask;
import a340.tickettoride.task.CreateGameTask;
import a340.tickettoride.task.DiscardDestCardTask;
import a340.tickettoride.task.JoinGameTask;
import a340.tickettoride.task.LoginTask;
import a340.tickettoride.task.RegisterTask;
import a340.tickettoride.task.SendChatTask;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
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

    public void sendChatMessage(ChatMessage message) {
        SendChatTask task = new SendChatTask(message);
        task.execute();
    }

    public void discardDestCards(List<DestinationCard> discardedCards) {
        DiscardDestCardTask task = new DiscardDestCardTask(discardedCards);
        task.execute();
    }

    public void claimRoute(Route route) {
        ClaimRouteTask task = new ClaimRouteTask(route);
        task.execute();
    }
}
