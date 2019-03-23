package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.List;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import a340.tickettoride.presenter.interfaces.IPlaceTrainsPresenter;
import cs340.TicketToRide.model.game.board.Route;

public class PlaceTrainsPresenter implements IPlaceTrainsPresenter {
    MapPresenter.View view;

    public PlaceTrainsPresenter(MapPresenter.View view) { this.view = view; }

    @Override
    public List<Route> getPossibleRoutesToClaim() {
        // todo: implement this
        return new ArrayList<>();
    }

    // remember to do all requirements: increase points, check for finished route, need required resources, choose what color if blank, double route rules correctly applied

    @Override
    public void placeTrains() {
        Route route = view.getSelectedRoute();
        ServiceFacade.getInstance().claimRoute(route);
    }

    @Override
    public void goBack() {

    }

    @Override
    public void finishTurn() {

    }




}
