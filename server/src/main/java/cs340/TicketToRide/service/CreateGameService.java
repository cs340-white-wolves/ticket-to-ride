package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;

public class CreateGameService {

    public Game createGame(AuthToken token) {
        if (token == null || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByAuthToken(token);
        if (user == null) {
            return null;
        }

        Game game = new Game();
        Player player = new Player(user.getUserID());
        game.addPlayer(player);
        model.addGame(game);

        return game;
    }
}
