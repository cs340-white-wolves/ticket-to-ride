package cs340.TicketToRide;

import java.util.List;

import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public interface IServer {
    LoginRegisterResponse login(Username username, Password password);
    LoginRegisterResponse register(Username username, Password password);
    Game createGame(AuthToken token, int numPlayers);
    Game joinGame(AuthToken token, ID gameId);
    Games getAvailableGames(AuthToken token);
    void sendChat(AuthToken token, ID gameId, ChatMessage message);
    Commands getQueuedCommands(AuthToken token, ID playerId, ID gameId, int index);
    void discardDestCards(DestinationCards cards, AuthToken token, ID gameId, ID playerId);
    void claimRoute(Route route, AuthToken token, ID gameID, ID playerId);
    /*
        SendChat
        getDestCards? they get automatically
        Return dest cards
     */
}
