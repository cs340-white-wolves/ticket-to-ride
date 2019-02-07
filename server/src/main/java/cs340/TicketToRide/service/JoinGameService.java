package cs340.TicketToRide.service;

import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.GameFullException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.ID;

public class JoinGameService {
    public Game joinGame(AuthToken token, ID gameID)
            throws GameFullException, AuthenticationException {
        if (token == null || gameID == null || !token.isValid() || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        Game game = model.getGameByID(gameID);
        if (game == null) {
            throw new RuntimeException("This game does not exist");
        }

        if (game.hasTargetNumPlayers()) {
            throw new GameFullException("This game already has its max number of players");
        }

        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new AuthenticationException("Invalid Auth Token");
        }

        Player player = new Player(user);

        if (!game.addPlayer(player)) {
            throw new RuntimeException("Error adding player to game");
        }

        return game;
    }
}
