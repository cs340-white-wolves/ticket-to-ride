package cs340.TicketToRide.communication;

import cs340.TicketToRide.IServer;

public interface IServerCommand {
    Object execute(IServer target);
}
