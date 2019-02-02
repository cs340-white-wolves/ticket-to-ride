package cs340.TicketToRide.communication;

public interface ICommand {
    public Object execute(Object target);
}
