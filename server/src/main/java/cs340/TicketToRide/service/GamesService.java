package cs340.TicketToRide.service;

import java.util.Set;

import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;

public class GamesService {
    // TODO: write a function that returns only lobby games

    // TODO: this function should be get games.
    public Games getAvailableGames(AuthToken token) {
        IServerModel model = ServerModel.getInstance();

        User user = model.getUserByAuthToken(token);

        if (user == null) {
            throw new AuthenticationException("Invalid Auth Token");
        }

        return ServerModel.getInstance().getGames();


//        Set<Game> games = ServerModel.getInstance().getGames().getGameSet();
//
//        Games availableGames = new Games();
//
//        for (Game game : games) {
//            if (!game.hasTargetNumPlayers()) {
//                try {
//                    availableGames.addGame(game);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return availableGames;
    }
}
