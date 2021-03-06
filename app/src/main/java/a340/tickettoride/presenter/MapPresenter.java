package a340.tickettoride.presenter;

import java.util.List;
import java.util.Set;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import cs340.TicketToRide.utility.RouteColorOption;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
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
        if (changeType == ModelChangeType.SetTurn) {
            onSetTurn((Integer) obj);
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
    public void onSetTurn(int playerIdx) {
        view.displayPlayerTurn(playerIdx);
        if (model.activePlayerTurn()) {
            model.startTurn();
            Game activeGame = model.getActiveGame();
            int destCardSize = activeGame.getDestCardDeck().size();
            int numTrainCards = activeGame.getTrainCardDeck().size()
                    + activeGame.getFaceUpTrainCards().size();
            view.enableButtons(destCardSize != 0, numTrainCards != 0);
            if (activeGame.getLastRoundLastPlayerId() != null) {
                view.displayLastTurn();
            }
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
    public boolean isActivePlayerTurn() {
        Game activeGame = model.getActiveGame();
        int idx = activeGame.getCurrentPlayerTurnIdx();
        Player player = activeGame.getPlayers().get(idx);

        return player.getId().equals(model.getPlayerId());
    }

    public interface View {
        void displayPlayerTurn(int playerIdx);
        void showRouteIsClaimed(Route route);
        DestinationCards getSelectedDestinationCards();
        DestinationCards getRecentlyAddedDestCards();
        Route getSelectedRoute();
        void enableButtons(final boolean enableRoutesBtn, final boolean enableCardsBtn);
        void disableButtons();
        void openDrawer(int side, boolean lockDrawer);
        void closeDrawer(int side);
        void displayResults(Players players);
        void chooseDestCard(DestinationCards cards, int minCardsToKeep);
        void displayLastTurn();
        void lockDrawer(boolean b);
        void initColorOptionsDialog(List<RouteColorOption> options);
    }
}
