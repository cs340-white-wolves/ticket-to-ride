package cs340.TicketToRide.service;

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
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.utility.ID;

public class ClaimRouteService {
    public void claimRoute(Route route, AuthToken token, ID gameID, ID playerId) {

        if (route.getOccupierId() != null) {
            throw new RuntimeException("This route is already occupied");
        }

        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new AuthenticationException("Invalid Auth Token");
        }

        Game game = model.getGameByID(gameID);
        if (game == null) {
            throw new RuntimeException("Invalid game ID");
        }

        Player player = game.getPlayerById(playerId);

        if (player == null) {
            throw new RuntimeException("Player not in game");
        }

        route.occupy(playerId);
        updatePlayerPoints(route, player, game);

        sendUpdates(route, game);
    }

    private void updatePlayerPoints(Route route, Player player, Game game) {
        player.setScore(player.getScore() + route.getPointValue());
        for (DestinationCard card : player.getDestinationCards()) {
            if (!card.isCompleted() && game.playerCompletedDestCard(player.getId(), card)) {
                card.setCompleted(true);
                player.setScore(player.getScore() + card.getPoints());
            }
        }
    }

    private void sendUpdates(Route route, Game game) {
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();
        Players players = game.getPlayers();

        for (Player player : players) {
            IClient client = proxyManager.get(player.getId());
            client.routeUpdated(route);
            client.playersUpdated(players);
        }
    }
}
