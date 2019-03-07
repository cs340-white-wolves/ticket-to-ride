package cs340.TicketToRide.service;

import java.util.List;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.utility.ID;

public class DiscardDestCardService {
    public void discardDestCards(List<DestinationCard> discardCards, AuthToken token, ID gameId, ID playerId) {
        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByAuthToken(token);
        if (user == null) {
            throw new AuthenticationException("User does not exist");
        }

        Game game = model.getGameByID(gameId);
        if (game == null) {
            throw new RuntimeException("Game does not exist");
        }

        Player player = game.getPlayerById(playerId);
        if (player == null) {
            throw new RuntimeException("Player not in game");
        }

        if (!player.getUser().equals(user)) {
            throw new RuntimeException("Player does not belong to user");
        }

        List<DestinationCard> allCards = player.getDestinationCards();
        if (!allCards.containsAll(discardCards)) {
            throw new RuntimeException("Player does not own these cards");
        }

        allCards.removeAll(discardCards);
        game.addCardsToDestCardDeck(discardCards);

        for (Player gamePlayer : game.getPlayers()) {
            IClient client = ClientProxyManager.getInstance().get(gamePlayer.getId());
            client.destCardDeckChanged(game.getDestCardDeck());
            client.playersUpdated(game.getPlayers());
        }

        // TODO: GAME HISTORY
    }
}