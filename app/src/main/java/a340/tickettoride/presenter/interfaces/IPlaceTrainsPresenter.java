package a340.tickettoride.presenter.interfaces;

import java.util.List;

import cs340.TicketToRide.utility.RouteColorOption;
import cs340.TicketToRide.model.game.board.Route;

public interface IPlaceTrainsPresenter {
    List<Route> getPossibleRoutesToClaim();
    boolean onSelectRoute();
    void startObserving();
    void stopObserving();
    void claimRoute(Route route, RouteColorOption selectedOption);
}
