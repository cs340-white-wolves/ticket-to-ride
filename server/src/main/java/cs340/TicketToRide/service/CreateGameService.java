package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;

public class CreateGameService {

    public Game createGame(AuthToken token, int numPlayers) throws Exception {
        if (token == null || !token.isValid() ||
                numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new Exception("The requesting user doesn't exist");
        }

        Game game = new Game(numPlayers);
        Player player = new Player(user.getUserID());

        if (!model.addGame(game)) {
            throw new Exception("Error creating game");
        }

        if (!game.addPlayer(player)) {
            throw new Exception("Error adding player to game");
        }
        return game;
    }
}
