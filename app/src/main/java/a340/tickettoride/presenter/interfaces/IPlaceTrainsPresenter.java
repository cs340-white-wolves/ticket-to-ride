package a340.tickettoride.presenter.interfaces;

import java.util.List;

import a340.tickettoride.utility.RouteColorOption;
import cs340.TicketToRide.model.game.board.Route;

public interface IPlaceTrainsPresenter {
    List<Route> getPossibleRoutesToClaim();
    void onSelectRoute();
    void goBack();
    void finishTurn();
    void startObserving();
    void stopObserving();
    void claimRoute(Route route, RouteColorOption selectedOption);
}
