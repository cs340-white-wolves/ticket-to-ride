package cs340.TicketToRide.model.db;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.game.Game;

public interface IGameDao extends IDao {
    void saveGame(Game game);
    Games loadGames();
    void saveCommand(Command command);
    Commands loadCommands();
    void clearCommands();
}
