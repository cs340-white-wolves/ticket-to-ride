package cs340.TicketToRide.service;

import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.utility.ID;

public class QueueService {
    public Commands getQueuedCommands(AuthToken token, ID playerId, ID gameId, int index) {
        IServerModel model = ServerModel.getInstance();

        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new AuthenticationException("Invalid Auth Token");
        }

        Game game = model.getGameByID(gameId);
        if (game == null) {
            throw new RuntimeException("Game doesn't exist");
        }

        Player player = game.getPlayerById(playerId);

        if (player == null) {
            throw new RuntimeException("Player not in game");
        }

        if (!player.getUser().equals(user)) {
            throw new AuthenticationException("Player does not belong to user");
        }

        // Law of demeter - defied!
        return ClientProxyManager.getInstance()
                .get(playerId)
                .getQueue()
                .getStartingFrom(index);
    }
}
