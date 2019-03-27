package a340.tickettoride.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import cs340.TicketToRide.model.game.Game;
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
        if (changeType == ModelChangeType.AdvanceTurn) {
            advanceTurn();
            view.lockDrawer(false);
        } else if (changeType == ModelChangeType.RouteClaimed) {
            view.showRouteIsClaimed((Route) obj);
        } else if (changeType == ModelChangeType.EndGame) {
            view.displayResults((Players) obj);
        } else if (changeType == ModelChangeType.SelectedSingleCard) {
            view.lockDrawer(true);
        }

    }

    public Set<City> getActiveGameCities() {
        return null;
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
    public void onClickDrawDestCards() {
        ServiceFacade.getInstance().drawDestCards();
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
    public void drawTrainCards() {
        //TODO: Maybe add check to make sure that it is the players turn
        model.takePlayerAction(ActionType.drawTrainCard);
    }

    @Override
    public boolean isActivePlayerTurn() {
        Game activeGame = model.getActiveGame();
        int idx = activeGame.getCurrentPlayerTurnIdx();
        Player player = activeGame.getPlayers().get(idx);

        return player.getId().equals(model.getPlayerId());
    }

    public interface View {
        void displayNextPlayersTurn();
        void showRouteIsClaimed(Route route);
        DestinationCards getSelectedDestinationCards();
        DestinationCards getRecentlyAddedDestCards();
        Route getSelectedRoute();
        void enableButtons();
        void disableButtons();
        void openDrawer(int side, boolean lockDrawer);
        void closeDrawer(int side);
        void displayResults(Players players);
        void chooseDestCard(DestinationCards cards, int minCardsToKeep);
        void lockDrawer(boolean b);
    }
}
