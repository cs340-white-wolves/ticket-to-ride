package cs340.TicketToRide.service;

import java.util.Set;

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
import cs340.TicketToRide.model.game.board.Board;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.RouteColorOption;

public class ClaimRouteService extends ActionService {
    public void claimRoute(Route route, RouteColorOption option, AuthToken token, ID gameID, ID playerId) {

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

        checkPlayerOwnsPartnerRoute(route, playerId, game);
        checkSmallGameDoubleRoute(route, game);

        removeTrainCards(option, route, player, game);
        removeTrainCars(route, player);

        route.occupy(playerId);

        recordClaim(route, game);

        updatePlayerPoints(route, player, game);

        boolean lastRound = handleIfStartOfLastRound(game, player);

        sendUpdates(route, game, player, lastRound);
    }

    public boolean handleIfStartOfLastRound(Game game, Player player) {
        if (game.getLastRoundLastPlayerId() == null && player.getNumTrains() <= 2) {
            game.setLastRoundLastPlayerId(player.getId());
            return true;
        }

        return false;
    }

    private void removeTrainCars(Route route, Player player) {
        int length = route.getLength();

        if (player.getNumTrains() < length) {
            throw new RuntimeException("Player does not have enough train cars to claim route.");
        }

        int numTrains = player.getNumTrains() - length;

        player.setNumTrains(numTrains);
    }

    /**
     * Remove the Train Cards form the player and return them to the game train card discard pile.
     * @param route the route the player wants to claim
     * @param player the player who want to claim the route
     * @param game the game itself
     */
    private void removeTrainCards(RouteColorOption option, Route route, Player player, Game game) {
        TrainCard.Color routeColor = route.getColor();
        int length = route.getLength();

        // todo: use the option now to remove the cards
        TrainCards trainCards = player.getTrainCards();

        if (! trainCards.hasColorCount(routeColor, length, true)) {
            throw new RuntimeException("Player does not have the right cards to claim route!");
        }

        TrainCards removedCards = trainCards.removeColorCount(routeColor, length, true);

        game.addDiscardedTrainCards(removedCards);
    }

    private void checkPlayerOwnsPartnerRoute(Route route, ID playerId, Game game) {
        if (route.isDoubleRoute()) {
            Route partnerRoute = game.getPartnerRoute(route);
            if (partnerRoute.occupiedBy(playerId)) {
                throw new RuntimeException("Player already owns the partner route");
            }
        }
    }

    private void checkSmallGameDoubleRoute(Route route, Game game) {
        Route partnerRoute = game.getPartnerRoute(route);
        if (route.isDoubleRoute() && ! game.canUseDoubleRoutes() && partnerRoute.getOccupierId() != null) {
            throw new RuntimeException("In a 2 or 3 player game, only one of the double routes may be claimed.");
        }
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

    private void sendUpdates(Route route, Game game, Player player, boolean startOfLastRound) {
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();
        Players players = game.getPlayers();
        String msg = "Claimed route from " + route.getCity1() + " to " + route.getCity2();
        Message historyMessage = new Message(player.getUser().getUsername(), msg);

        game.setNextPlayerTurn();

        for (Player curPlayer : players) {
            IClient client = proxyManager.get(curPlayer.getId());
            client.routeUpdated(route);
            client.playersUpdated(players);
            client.historyMessageReceived(historyMessage);
            client.setTurn(game.getCurrentPlayerTurnIdx());
        }

        if (startOfLastRound) {
            String message = "The last round has begun!";
            Message lastRound = new Message(player.getUser().getUsername(), message);

            for (Player curPlayer : players) {
                IClient client = proxyManager.get(curPlayer.getId());
                client.historyMessageReceived(lastRound);
                client.setLastRoundLastPlayer(player.getId());
            }
        }

        if (!checkToEndGame(game)){
            increaseTurnsPassed(game);
        }
    }

    private void recordClaim(Route route, Game game) {
        Board board = game.getBoard();
        Set<Route> routes = board.getRoutes();
        routes.remove(route);
        routes.add(route);
    }
}
