package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IPlaceTrainsPresenter;
import cs340.TicketToRide.utility.RouteColorOption;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public class PlaceTrainsPresenter implements IPlaceTrainsPresenter, ModelObserver {
    private MapPresenter.View view;
    private IClientModel model = ClientModel.getInstance();

    public PlaceTrainsPresenter(MapPresenter.View view) { this.view = view; }

    @Override
    public List<Route> getPossibleRoutesToClaim() {
        List<Route> possible = new ArrayList<>();
        Game game = model.getActiveGame();
        Player player = game.getPlayerById(model.getPlayerId());

        Set<Route> routes = game.getBoard().getRoutes();

        TrainCards trainCards = player.getTrainCards();
        Map<TrainCard.Color, Integer> colorCounts = trainCards.getColorCounts(false);
        int locomotiveCount = trainCards.getColorCount(TrainCard.Color.locomotive);

        for (Route route : routes) {
            if (routeAvailable(possible, game, player, route)) {
                boolean canClaim = false;
                if (route.getColor() == null) {
                    for (Integer count : colorCounts.values()) {
                        if (locomotiveCount + count >= route.getLength()) {
                            canClaim = true;
                            break;
                        }
                    }
                } else {
                    int colorCount = trainCards.getColorCount(route.getColor());
                    canClaim = colorCount + locomotiveCount >= route.getLength();
                }

                if (canClaim) {
                    possible.add(route);
                }
            }
        }
        return possible;
    }

    private boolean routeAvailable(List<Route> possible, Game game, Player player, Route route) {
        return route.unOccupied() && notAlreadyAdded(possible, route) && (game.canUseDoubleRoutes()
                || partnerUnoccupied(route, game)) && !playerOwnsPartner(player, route, game);
    }

    private boolean partnerUnoccupied(Route route, Game game) {
        Route partnerRoute = game.getPartnerRoute(route);
        return partnerRoute == null || partnerRoute.unOccupied();
    }

    private boolean notAlreadyAdded(List<Route> possible, Route route) {
        return !route.isDoubleRoute() || !isDuplicate(route, possible);
    }

    private boolean isDuplicate(Route route, List<Route> addedRoutes) {
        for (Route addedRoute : addedRoutes) {
            if (addedRoute.isDuplicate(route)) {
                return true;
            }
        }
        return false;
    }

    private boolean playerOwnsPartner(Player player, Route route, Game game) {
        if (!route.isDoubleRoute()) { return false; }
        Route partnerRoute = game.getPartnerRoute(route);
        return partnerRoute.occupiedBy(player.getId());
    }

    // todo: remember to complete requirements:
        // choose what color if blank
        // end turn appropriately
        // check for finished destination

    @Override
    public void onSelectRoute() {
        Route route = view.getSelectedRoute();
        List<RouteColorOption> options = getColorOptions(route);
        view.initColorOptionsDialog(options);
    }

    private List<RouteColorOption> getColorOptions(Route route) {
        Player player = model.getActiveGame().getPlayerById(model.getPlayerId());
        TrainCards trainCards = player.getTrainCards();
        Map<TrainCard.Color, Integer> colorCounts = trainCards.getColorCounts(false);
        List<RouteColorOption> options = new ArrayList<>();
        int length = route.getLength();

        int numLocomotives = getCountSafe(colorCounts, TrainCard.Color.locomotive);
        TrainCard.Color routeColor = route.getColor();

        if (routeColor == null) {
            for (Map.Entry<TrainCard.Color, Integer> entry : colorCounts.entrySet()) {
                TrainCard.Color color = entry.getKey();
                int numOfColor = entry.getValue();

                calcOptionsForColor(options, numLocomotives, color, numOfColor, length);
            }
        } else {
            int colorCount = getCountSafe(colorCounts, routeColor);
            calcOptionsForColor(options, numLocomotives, routeColor, colorCount, length);
        }

        if (numLocomotives >= length) {
            options.add(new RouteColorOption(null, 0, length));
        }

        return options;
    }

    private int getCountSafe(Map<TrainCard.Color, Integer> colorCounts, TrainCard.Color color) {
        Integer count = colorCounts.get(color);
        return count == null ? 0 : count;
    }

    private void calcOptionsForColor(List<RouteColorOption> options, Integer numLocomotives, TrainCard.Color color, int numOfColor, int length) {
        if (numOfColor >= length) {
            options.add(new RouteColorOption(color, length, 0));
        } else if (numOfColor + numLocomotives >= length){
            int namLocomotivesNeeded = length - numOfColor;
            options.add(new RouteColorOption(color, numOfColor, namLocomotivesNeeded));
        }
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
    public void claimRoute(Route route, RouteColorOption selectedOption) {
        ServiceFacade.getInstance().claimRoute(route, selectedOption);
    }


    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {

    }
}
