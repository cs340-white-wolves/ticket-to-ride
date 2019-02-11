package a340.tickettoride.presenter;

import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.utility.ID;

public interface IJoinGamePresenter {
    void joinGame(ID gameID);
    void startObserving();
    void stopObserving();
//    Games getLobbyGames();
}
