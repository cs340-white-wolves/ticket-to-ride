package cs340.TicketToRide.service;

import cs340.TicketToRide.ClientProxy;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;
import cs340.TicketToRide.utility.ID;

public class DrawTrainCardService {
    public void drawTrainCard(TrainCard card, AuthToken token, ID gameId, ID playerId) {
        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByAuthToken(token);
        if (user == null) {
            throw new AuthenticationException("Invalid Auth Token");
        }

        Game game = model.getGameByID(gameId);
        if (game == null) {
            throw new RuntimeException("Invalid game ID");
        }

        Player player = game.getPlayerById(playerId);

        if (player == null) {
            throw new RuntimeException("Player not in game");
        }

        addCardToPlayer(game, card, player);

    }

    private void addCardToPlayer(Game game, TrainCard card, Player player) {
        TrainCards trainCardDeck = game.getTrainCardDeck();
        TrainCards faceUpTrainCards = game.getFaceUpTrainCards();

        // Some differences if they choose a face up vs a card from the deck
        if (trainCardDeck.contains(card)) {
            trainCardDeck.remove(card);
            player.addTrainCard(card);
            updateGame(game, false);
        } else if (faceUpTrainCards.contains(card)) {
            faceUpTrainCards.remove(card);
            player.addTrainCard(card);
            TrainCard newFaceUpCard = trainCardDeck.drawFromTop();
            faceUpTrainCards.add(newFaceUpCard);
            updateGame(game, true);
        } else {
            throw new RuntimeException("Card not in faceup or facedown decks");
        }
    }

    private void updateGame(Game game, boolean faceUp) {
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();
        // todo: create game hist obj based on faceup
        Players players = game.getPlayers();
        for (Player player : players) {
            IClient client = proxyManager.get(player.getId());
            client.playersUpdated(players);
            if (faceUp) {
                client.faceUpDeckChanged(game.getFaceUpTrainCards());
            } else {
                client.trainCardDeckChanged(game.getTrainCardDeck());
            }
        }
    }
}
