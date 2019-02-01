package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;

public interface IServerModel {
    User getUserByAuthToken(AuthToken token);
    void addGame(Game game);
    Game getGameByID(ID gameID);
}
