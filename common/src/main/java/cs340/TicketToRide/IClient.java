package cs340.TicketToRide;

import cs340.TicketToRide.utility.ID;

public interface IClient {
    void updatePlayerCount(ID gameId, int count);
    void updateGameList();
}
