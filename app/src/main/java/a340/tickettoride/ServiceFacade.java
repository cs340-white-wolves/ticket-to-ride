package a340.tickettoride;

import a340.tickettoride.task.ClaimRouteTask;
import a340.tickettoride.task.CreateGameTask;
import a340.tickettoride.task.DiscardDestCardTask;
import a340.tickettoride.task.DrawDestCardsTask;
import a340.tickettoride.task.DrawTrainCardTask;
import a340.tickettoride.task.JoinGameTask;
import a340.tickettoride.task.LoginTask;
import a340.tickettoride.task.RegisterTask;
import a340.tickettoride.task.SendChatTask;
import cs340.TicketToRide.utility.RouteColorOption;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
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

    public void sendChatMessage(Message message) {
        SendChatTask task = new SendChatTask(message);
        task.execute();
    }

    public void drawDestCards() {
        DrawDestCardsTask task = new DrawDestCardsTask();
        task.execute();
    }

    public void discardDestCards(DestinationCards discardedCards) {
        DiscardDestCardTask task = new DiscardDestCardTask(discardedCards);
        task.execute();
    }

    public void claimRoute(Route route, RouteColorOption option) {
        ClaimRouteTask task = new ClaimRouteTask(route, option);
        task.execute();
    }

    public void drawTrainCard(TrainCard card, boolean advanceTurn) {
        DrawTrainCardTask task = new DrawTrainCardTask(card, advanceTurn);
        task.execute();
    }
}
