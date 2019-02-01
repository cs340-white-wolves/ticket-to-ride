package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.ID;

public class JoinGameService {
    public Game joinGame(AuthToken token, ID gameID) {
        if (token == null || gameID == null || !token.isValid() || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        Game game = model.getGameByID(gameID);
        if (game == null) {
            // todo: throw exception?
            return null;
        }

        if (game.hasTargetNumPlayers()) {
            // todo: throw exception?
            return null;
        }

        User user = model.getUserByAuthToken(token);
        if (user == null) {
            // todo: throw exception?
            return null;
        }

        Player player = new Player(user.getUserID());
        game.addPlayer(player);
        return game; // todo: do we want to return Game? or just a message?
    }
}
