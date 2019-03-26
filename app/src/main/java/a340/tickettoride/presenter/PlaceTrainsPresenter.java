package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import a340.tickettoride.presenter.interfaces.IPlaceTrainsPresenter;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.TrainCards;

public class PlaceTrainsPresenter implements IPlaceTrainsPresenter, ModelObserver {
    MapPresenter.View view;
    IClientModel model = ClientModel.getInstance();

    public PlaceTrainsPresenter(MapPresenter.View view) { this.view = view; }

    @Override
    public List<Route> getPossibleRoutesToClaim() {
        List<Route> possible = new ArrayList<>();
        Set<Route> routes = model.getActiveGame().getBoard().getRoutes();
        Player player = model.getActiveGame().getPlayerById(model.getPlayerId());
        TrainCards trainCards = player.getTrainCards();
        for (Route route : routes) {
            if (route.getOccupierId() == null) {
                boolean canClaim = trainCards.hasColorCount(route.getColor(), route.getLength(), true);
                if (canClaim) {
                    possible.add(route);
                }
            }
        }
        return possible;
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

    @Override
    public void startObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void stopObserving() {
        ClientModel.getInstance().deleteObserver(this);
    }


    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {

    }
}
