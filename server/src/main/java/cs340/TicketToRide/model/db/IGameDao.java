package cs340.TicketToRide.model.db;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.game.Game;

public interface IGameDao extends IDao {
    Games loadGames();
    void saveCommand(Command command);
    Commands loadCommands();
    void clearCommands();
    void saveClientManager(ClientProxyManager manager);
    ClientProxyManager loadClientManager();
    void saveGames(Games games);
}
