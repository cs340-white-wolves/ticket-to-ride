package cs340.TicketToRide.service;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;
import cs340.TicketToRide.utility.ID;

public abstract class ActionService {

    final int AWARD_POINTS = 10;

    protected boolean checkToEndGame(Game game) {
        ID currentPlayerId = game.getPlayers().get(game.getCurrentPlayerTurnIdx()).getId();

        if (game.getLastRoundLastPlayerId() == currentPlayerId) {
            assignPoints(game.getPlayers());
            sendEndGameCommand(game);
            return true;
        }

        return false;
    }

    private void assignPoints(Players players) {

        int maxDestinationsCompletedCnt = findMostDestCompleted(players);

        for (Player player: players) {
            if (player.getNumOfCompletedDests() == maxDestinationsCompletedCnt) {
                player.setAwardPoints(AWARD_POINTS);
            }

            player.updateTotalPoints();
        }
    }

    private int findMostDestCompleted(Players players) {

        int mostRoutes = 0;

        for (Player player: players) {
            if (player.getNumOfCompletedDests() > mostRoutes) {
                mostRoutes = player.getNumOfCompletedDests();
            }
        }

        return mostRoutes;
    }

    protected void sendEndGameCommand(Game game) {
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();
        Players players = game.getPlayers();
        for (Player curPlayer : players) {
            IClient client = proxyManager.get(curPlayer.getId());
            client.playersUpdated(players);
            client.endGame();
        }
    }

    protected void replaceFaceUpCard(Game game, TrainCards trainCardDeck,
                                   TrainCards faceUpTrainCards) {
        TrainCard newFaceUpCard = trainCardDeck.drawFromTop();

        if (newFaceUpCard != null) {
            faceUpTrainCards.add(newFaceUpCard);
        }

        if (game.hasTooManyFaceupLocomotives()) {
            game.setupFaceUpCards();
        }
    }
}
