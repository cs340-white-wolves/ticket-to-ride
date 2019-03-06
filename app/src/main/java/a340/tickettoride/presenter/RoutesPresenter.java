package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.presenter.interfaces.IRoutesPresenter;
import cs340.TicketToRide.model.game.card.DestinationCard;

public class RoutesPresenter implements IRoutesPresenter {
    public interface View {
        void updatePlayerDestCardDisplay(List<DestinationCard> cards);
    }
}
