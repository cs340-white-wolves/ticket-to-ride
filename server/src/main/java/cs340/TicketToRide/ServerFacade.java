package cs340.TicketToRide;

import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.*;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.service.*;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.RouteColorOption;
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

    public LoginRegisterResponse login(Username username, Password password) {
        return new LoginService().login(username, password);
    }

    public LoginRegisterResponse register(Username username, Password password) {
        return new RegisterService().register(username, password);
    }

    public Game createGame(AuthToken token, int numPlayers) {
        return new CreateGameService().createGame(token, numPlayers);
    }

    public Game joinGame(AuthToken token, ID gameId) {
        return new JoinGameService().joinGame(token, gameId);
    }

    public Commands getQueuedCommands(AuthToken token, ID playerId, ID gameId, int index) {
        return new QueueService().getQueuedCommands(token, playerId, gameId, index);
    }

    @Override
    public void discardDestCards(DestinationCards cards, AuthToken token, ID gameId, ID playerId) {
        new DiscardDestCardService().discardDestCards(cards, token, gameId, playerId);
    }

    @Override
    public void claimRoute(Route route, RouteColorOption option, AuthToken token, ID gameID, ID playerId) {
        new ClaimRouteService().claimRoute(route, option, token, gameID, playerId);
    }

    @Override
    public void drawTrainCard(TrainCard card, boolean advanceTurn, AuthToken token, ID gameId, ID playerId) {
        new DrawTrainCardService().drawTrainCard(card, advanceTurn, token, gameId, playerId);
    }

    @Override
    public void drawDestCards(AuthToken token, ID gameId, ID playerId) {
        new DrawDestCardsService().drawDestCards(token, gameId, playerId);
    }

    public Games getAvailableGames(AuthToken token) {
        return new GamesService().getAvailableGames(token);
    }

    @Override
    public void sendChat(AuthToken token, ID gameId, Message message) {
        new SendChatService().sendChat(token, gameId, message);
    }

}
