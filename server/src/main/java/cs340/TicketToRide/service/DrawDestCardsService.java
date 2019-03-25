package cs340.TicketToRide.service;

import cs340.TicketToRide.ClientProxy;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.utility.ID;

public class DrawDestCardsService {
    public void drawDestCards(AuthToken token, ID gameId, ID playerId) {
        IServerModel model = ServerModel.getInstance();
        if (model.getUserByAuthToken(token) == null) {
            throw new AuthenticationException("Invalid auth token");
        }

        Game game = model.getGameByID(gameId);
        if (game == null) {
            throw new RuntimeException("Invalid game id");
        }

        Player player = game.getPlayerById(playerId);
        if (player == null) {
            throw new RuntimeException("Invalid player id");
        }
        DestinationCards destCardDeck = game.getDestCardDeck();
        DestinationCards cardsToAddToPlayer = new DestinationCards();
        for (int i = 0; i < 3; i++) {
            DestinationCard card = destCardDeck.drawFromTop();
            if (card != null) {
                cardsToAddToPlayer.add(card);
            }
        }

        player.getDestinationCards().addAll(cardsToAddToPlayer);
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();
        IClient client = proxyManager.get(playerId);
        client.addedDestCards(cardsToAddToPlayer);

        for (Player curPlayer : game.getPlayers()) {
            client = proxyManager.get(curPlayer.getId());
            client.playersUpdated(game.getPlayers());
            client.destCardDeckChanged(game.getDestCardDeck());
        }


    }
}
