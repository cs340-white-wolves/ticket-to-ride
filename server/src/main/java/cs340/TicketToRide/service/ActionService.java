package cs340.TicketToRide.service;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.utility.ID;

public abstract class ActionService {

    final int AWARD_POINTS = 10;

    protected boolean checkToEndGame(Game game) {
        ID currentPlayerId = game.getPlayers().get(game.getCurrentPlayerTurnIdx()).getId();

        if (game.getLastRoundLastPlayerId() == currentPlayerId) {
            assignAwardPoints(game.getPlayers());
            sendEndGameCommand(game);
            return true;
        }

        return false;
    }



    private void assignAwardPoints(Players players) {

        int destCompleted = findMostDestCompleted(players);

        for (Player player: players) {
            if (player.getNumOfCompletedDests() == destCompleted) {
                player.setScore(player.getScore() + AWARD_POINTS);
                player.setAward(true);
            }
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
}
