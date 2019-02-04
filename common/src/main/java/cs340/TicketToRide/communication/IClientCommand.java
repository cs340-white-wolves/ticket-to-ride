package cs340.TicketToRide.communication;

import cs340.TicketToRide.IClient;

public interface IClientCommand {
    public Object execute(IClient target);
}
