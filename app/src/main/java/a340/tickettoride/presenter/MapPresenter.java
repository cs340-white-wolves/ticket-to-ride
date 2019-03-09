package a340.tickettoride.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.utility.ID;

public class MapPresenter implements IMapPresenter, ModelObserver {
    private View view;
    private IClientModel model = ClientModel.getInstance();

    public MapPresenter(View view) {
        this.view = view;
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
        if (changeType == ModelChangeType.GameStarted) {
            Log.i("MapPresenter", "Game Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            view.chooseDestCard();
        }
    }

    public Set<City> getActiveGameCities() {
        return null;
    }

    @Override
    public void discardDestCards() {
        DestinationCards selectedCards = view.getSelectedDestinationCards();
        DestinationCards discardedCards = new DestinationCards();
        Player player = model.getPlayerFromGame();
        DestinationCards allCards = player.getDestinationCards();
        for (DestinationCard card : allCards) {
            if (!selectedCards.contains(card)) {
                discardedCards.add(card);
            }
        }

        ServiceFacade.getInstance().discardDestCards(discardedCards);
    }

    @Override
    public void placeTrains() {
        Route route = view.getSelectedRoute();
        ServiceFacade.getInstance().claimRoute(route);
        // todo: implement this
    }

    @Override
    public Set<City> getCities() {
        return model.getActiveGame().getBoard().getCities();
    }

    @Override
    public Set<Route> getRoutes() {
        return model.getActiveGame().getBoard().getRoutes();
    }

    @Override
    public void advanceTurn() {
        view.displayNextPlayersTurn();
        if (model.activePlayerTurn()) {
            view.enableButtons();
        } else {
            view.disableButtons();
        }
    }

    @Override
    public void startPoller() {
        model.startGameCommandPoller();
    }

    @Override
    public Players getPlayers() {
        return model.getActiveGame().getPlayers();
    }

    @Override
    public Player getPlayerById(ID playerId) {
        return model.getActiveGame().getPlayerById(playerId);
    }

    @Override
    public List<Route> getPossibleRoutesToClaim() {
        // todo: implement this
        return new ArrayList<>();
    }

    @Override
    public DestinationCards getPlayerDestCards() {
        return model.getPlayerFromGame().getDestinationCards();
    }

    public interface View {
        void displayNextPlayersTurn();
        void showRouteIsClaimed(Route route);
        DestinationCards getSelectedDestinationCards();
        Route getSelectedRoute();
        void enableButtons();
        void disableButtons();
        void chooseDestCard();
    }
}
