package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IPlaceTrainsPresenter;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.TrainCards;

public class PlaceTrainsPresenter implements IPlaceTrainsPresenter, ModelObserver {
    private static final int NUM_PLAYERS_DUPLICATE_ROUTES = 4;
    MapPresenter.View view;
    IClientModel model = ClientModel.getInstance();

    public PlaceTrainsPresenter(MapPresenter.View view) { this.view = view; }

    @Override
    public List<Route> getPossibleRoutesToClaim() {
        List<Route> possible = new ArrayList<>();
        Set<Route> routes = model.getActiveGame().getBoard().getRoutes();
        if (!allowDuplicateRoutes()) {
            routes = deleteDuplicateRoutes(routes);
        }
        Player player = model.getActiveGame().getPlayerById(model.getPlayerId());
        TrainCards trainCards = player.getTrainCards();
        for (Route route : routes) {
            if (route.getOccupierId() == null) {
                boolean hasResources = trainCards.hasColorCount(route.getColor(), route.getLength(), true);
                if (hasResources && !isDoubleRoute(possible, route) && !playerHasDuplicateRoute(player, route)) {
                    possible.add(route);
                }
            }
        }
        return possible;
    }

    private boolean allowDuplicateRoutes() {
        if (model.getActiveGame().getTargetNumPlayers() == NUM_PLAYERS_DUPLICATE_ROUTES) {
            return true;
        }

        return false;
    }

    private Set<Route> deleteDuplicateRoutes(Set<Route> routes) {
        List<Route> newRoutes = new ArrayList<>();
        for (Route route : routes) {
            if (!isDoubleRoute(newRoutes, route)) {
                newRoutes.add(route);
            }
        }

        Set<Route> returnRoutes = new HashSet<>();
        returnRoutes.addAll(newRoutes);

        return returnRoutes;
    }

    private boolean playerHasDuplicateRoute(Player player, Route route) {
        if (!route.isDoubleRoute()) { return false; }
        for (Route r : player.getClaimedRoutes()) {
            if (r.isPartnerRoute(route)) { return true; }
        }

        return false;
    }

    private boolean isDoubleRoute(List<Route> possible, Route route) {
        for (Route r : possible) {
            if (r.isPartnerRoute(route)) { return true; }
        }

        return false;
    }

    // remember to do all requirements:
        //// CHECK // need required resources
        // after claiming, take away correct resources
        // increase points
        //// CHECK // can't claim route if already claimed
        // choose what color if blank
        // end turn appropriately
        //// CHECK //  double route rules correctly applied (1 player can't own both routes; can't claim both if less than 4 players)
        // check for finished destination

    @Override
    public void placeTrains() {
        Route route = view.getSelectedRoute();
        ServiceFacade.getInstance().claimRoute(route);
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
