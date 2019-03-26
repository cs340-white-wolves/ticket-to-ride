package a340.tickettoride.presenter.interfaces;

import java.util.List;

import cs340.TicketToRide.model.game.board.Route;

public interface IPlaceTrainsPresenter {
    List<Route> getPossibleRoutesToClaim();
    void placeTrains();
    void goBack();
    void finishTurn();
    void startObserving();
    void stopObserving();

}
