package cs340.TicketToRide.relational;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.db.IGameDao;
import cs340.TicketToRide.model.game.Game;

public class RDGameDao implements IGameDao {
    @Override
    public void saveGame(Game game) {

    }

    @Override
    public Games loadGames() {
        return null;
    }

    @Override
    public void saveCommand(Command command) {

    }

    @Override
    public Commands loadCommands() {
        return null;
    }

    @Override
    public void clearCommands() {

    }

    @Override
    public void saveClientManager(ClientProxyManager manager) {

    }

    @Override
    public ClientProxyManager loadClientManager() {
        return null;
    }

    @Override
    public void saveGames(Games games) {

    }

    @Override
    public void beginTransaction() {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void clearAll() {

    }
}