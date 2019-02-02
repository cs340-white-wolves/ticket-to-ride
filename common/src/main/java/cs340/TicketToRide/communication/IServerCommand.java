package cs340.TicketToRide.communication;

import cs340.TicketToRide.IServer;

public interface IServerCommand {
    public Object execute(IServer target);
}
