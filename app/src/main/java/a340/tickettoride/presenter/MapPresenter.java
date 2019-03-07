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
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;

public class MapPresenter implements IMapPresenter, ModelObserver {
    private View view;
    private IClientModel model = ClientModel.getInstance();

    public MapPresenter(View view) {
        this.view = view;
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {

    }

    public Set<City> getActiveGameCities() {
        return null;
    }

    public void discardDestCards() {
        List<DestinationCard> selectedCards = view.getSelectedDestinationCards();
        List<DestinationCard> discardedCards = new ArrayList<>();
        Player player = model.getPlayerFromGame();
        List<DestinationCard> allCards = player.getDestinationCards();
        for (DestinationCard card : allCards) {
            if (!selectedCards.contains(card)) {
                discardedCards.add(card);
            }
        }

        ServiceFacade.getInstance().discardDestCards(discardedCards);
    }

    public interface View {
        void displayNextPlayersTurn();
        void showRouteIsClaimed(Route route);
        List<DestinationCard> getSelectedDestinationCards();
        Route getSelectedRoute();
    }
}
