package cs340.TicketToRide.service;

import java.util.Set;

import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.ServerModel;

public class GamesService {
    public Games getAvailableGames() {
        Set<Game> games = ServerModel.getInstance().getGames().getGameSet();
        Games availableGames = new Games();

        for (Game game : games) {
            if (!game.hasTargetNumPlayers()) {
                try {
                    availableGames.addGame(game);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return availableGames;
    }
}
