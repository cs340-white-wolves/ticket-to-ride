package a340.tickettoride.presenter;

import java.util.List;
import java.util.Set;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IRoutesPresenter;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.utility.ID;

public class RoutesPresenter implements IRoutesPresenter, ModelObserver {

    private View view;
    private IClientModel model = ClientModel.getInstance();

    public RoutesPresenter(View view) {
        this.view = view;
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
