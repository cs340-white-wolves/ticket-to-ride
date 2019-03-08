package cs340.TicketToRide.service;

import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;

public class CreateGameService {

    public Game createGame(AuthToken token, int numPlayers) throws AuthenticationException {
        if (token == null || !token.isValid() ||
                numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new AuthenticationException("Invalid Auth Token");
        }

        Game game = new Game(numPlayers, user.getUsername());
        Player player = new Player(user);

        if (!model.addGame(game)) {
            throw new RuntimeException("Error creating game");
        }

        if (!game.addPlayer(player)) {
            throw new RuntimeException("Error adding player to game");
        }

        ClientProxyManager.getInstance().create(player.getId());

        return game;
    }
}
