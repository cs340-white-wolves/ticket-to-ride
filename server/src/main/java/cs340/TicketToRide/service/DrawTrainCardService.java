package cs340.TicketToRide.service;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;
import cs340.TicketToRide.utility.ID;

public class DrawTrainCardService extends ActionService {

    public static final int SINGLE_CARD = 1;

    public void drawTrainCard(TrainCard card, boolean advanceTurn, AuthToken token, ID gameId, ID playerId) {
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

        addCardToPlayer(game, card, player, advanceTurn);

    }

    private void addCardToPlayer(Game game, TrainCard card, Player player, boolean advanceTurn) {
        TrainCards trainCardDeck = game.getTrainCardDeck();
        TrainCards faceUpTrainCards = game.getFaceUpTrainCards();
        boolean faceup;
        String msg;

        if (trainCardDeck.size() <= SINGLE_CARD) {
            game.addDiscardedToDrawDeck();
        }

        // Some differences if they choose a face up vs a card from the deck
        if (trainCardDeck.contains(card)) {
            trainCardDeck.remove(card);
            msg = "Drew train card from deck";
            faceup = false;
        } else if (faceUpTrainCards.contains(card)) {
            faceUpTrainCards.remove(card);
            replaceFaceUpCard(game, trainCardDeck, faceUpTrainCards);
            msg = "Drew face up " + card.toString() + " train card";
            faceup = true;
        } else {
            throw new RuntimeException("Card not in faceup or facedown decks");
        }

        Message historyMessage = new Message(player.getUser().getUsername(), msg);
        player.addTrainCard(card);
        updateGame(game, faceup, historyMessage, advanceTurn);
    }

    private void updateGame(Game game, boolean faceup, Message historyMessage, boolean advanceTurn) {
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();
        Players players = game.getPlayers();

        for (Player curPlayer : players) {
            IClient client = proxyManager.get(curPlayer.getId());
            client.playersUpdated(players);

            if (faceup) {
                client.faceUpDeckChanged(game.getFaceUpTrainCards());
            }

            client.trainCardDeckChanged(game.getTrainCardDeck());
            client.historyMessageReceived(historyMessage);
        }

        if (advanceTurn) {
            boolean end = checkToEndGame(game);

            if (! end) {
                game.setNextPlayerTurn();

                for (Player curPlayer : players) {
                    IClient client = proxyManager.get(curPlayer.getId());
                    client.setTurn(game.getCurrentPlayerTurnIdx());
                }
            }
        }
    }
}
