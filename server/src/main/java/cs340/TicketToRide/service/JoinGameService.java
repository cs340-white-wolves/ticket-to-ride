package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.ID;

public class JoinGameService {
    public Game joinGame(AuthToken token, ID gameID) throws Exception {
        if (token == null || gameID == null || !token.isValid() || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        Game game = model.getGameByID(gameID);
        if (game == null) {
            throw new Exception("This game does not exist");
        }

        if (game.hasTargetNumPlayers()) {
            throw new Exception("This game already has its max number of players");
        }

        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new Exception("The requesting user doesn't exist");
        }

        Player player = new Player(user);

        if (!game.addPlayer(player)) {
            throw new Exception("Error adding player to game");
        }

        return game;
    }
}
