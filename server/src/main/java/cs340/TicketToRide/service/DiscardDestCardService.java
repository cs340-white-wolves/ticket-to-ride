package cs340.TicketToRide.service;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.*;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.utility.ID;

public class DiscardDestCardService extends ActionService {
    public void discardDestCards(DestinationCards discardCards, AuthToken token, ID gameId, ID playerId) {
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

        DestinationCards allCards = player.getDestinationCards();
        if (!allCards.containsAll(discardCards)) {
            throw new RuntimeException("Player does not own these cards");
        }

        allCards.removeAll(discardCards);
        game.addCardsToDestCardDeck(discardCards);
        String msg = "Drew " + (3 - discardCards.size()) + " destination cards";
        Message historyMessage = new Message(player.getUser().getUsername(), msg);

        if (!game.allPlayersReady()) {
            game.decrementPlayersLeftToDiscard();
        } else {
            game.setNextPlayerTurn();
        }

        for (Player gamePlayer : game.getPlayers()) {
            IClient client = ClientProxyManager.getInstance().get(gamePlayer.getId());
            client.destCardDeckChanged(game.getDestCardDeck());
            client.playersUpdated(game.getPlayers());
            client.historyMessageReceived(historyMessage);
            if (game.allPlayersReady()) {
                client.setTurn(game.getCurrentPlayerTurnIdx());
            }
        }

        checkToEndGame(game);

    }
}
