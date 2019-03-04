package cs340.TicketToRide.service;

import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.utility.ID;

public class QueueService extends Commands {
    public Commands getQueuedCommands(AuthToken token, ID playerId, int index) {
        IServerModel model = ServerModel.getInstance();

        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new AuthenticationException("Invalid Auth Token");
        }

        return model.getClientQueueManager().getAfter(playerId, index);
    }
}
