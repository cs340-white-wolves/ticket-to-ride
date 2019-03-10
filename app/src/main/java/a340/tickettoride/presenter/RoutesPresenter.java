package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IRoutesPresenter;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.utility.ID;

public class RoutesPresenter implements IRoutesPresenter, ModelObserver {

    private View view;
    private IClientModel model = ClientModel.getInstance();


    public RoutesPresenter(View view) {
        this.view = view;
    }

    public List<DestinationCard> getAllRoutes() {
        List<DestinationCard> playersCards = new ArrayList<>();

        DestinationCard card = new DestinationCard(new City("Vancouver", "VAN", 50.2827,-123.1207), new City("Seattle", "SEA", 47.6062, -122.3321), 3);
        DestinationCard card1 = new DestinationCard(new City("Portland", "POR", 45.0, -122.6587), new City("Seattle", "SEA", 47.6062, -122.3321), 3);
        DestinationCard card2 = new DestinationCard(new City("San Francisco", "SFO", 37.7749, -122.4194), new City("Seattle", "SEA", 47.6062, -122.3321), 3);
        DestinationCard card3 = new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3);
        DestinationCard card4 = new DestinationCard(new City("Phoenix", "PHX", 35.1983, -111.6513), new City("Seattle", "SEA", 47.6062, -122.3321), 3);

        playersCards.add(card);
        playersCards.add(card1);
        playersCards.add(card2);
        playersCards.add(card3);
        playersCards.add(card4);

        return playersCards;
    }

    public Set<DestinationCard> getCompletedRoutes() {
        Set<DestinationCard> playersCards = new HashSet<>();

        DestinationCard card = new DestinationCard(new City("Vancouver", "VAN", 50.2827,-123.1207), new City("Seattle", "SEA", 47.6062, -122.3321), 3);
        DestinationCard card1 = new DestinationCard(new City("Portland", "POR", 45.0, -122.6587), new City("Seattle", "SEA", 47.6062, -122.3321), 3);

        playersCards.add(card);
        playersCards.add(card1);

        return playersCards;
    }




    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.UpdatePlayerHand) {
            Player player = (Player) obj;
            Game game = model.getActiveGame();
            Set<DestinationCard> completedDestCards = game.getPlayerCompletedDestCards(player.getId());
            view.updatePlayerDestCardDisplay(completedDestCards, player.getDestinationCards());
        }
    }

    @Override
    public DestinationCards getPlayerDestCards() {
        Game game = model.getActiveGame();
        ID playerId = model.getPlayerId();
        Player player = game.getPlayerById(playerId);
        return player.getDestinationCards();
    }

    public interface View {
        void updatePlayerDestCardDisplay(Set<DestinationCard> completedCards, DestinationCards allCards);
    }
}
