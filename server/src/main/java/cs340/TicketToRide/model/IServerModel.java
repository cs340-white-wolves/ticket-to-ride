package cs340.TicketToRide.model;

public interface IServerModel {
    User getUserByAuthToken(AuthToken token);
    void addGame(Game game);
}
