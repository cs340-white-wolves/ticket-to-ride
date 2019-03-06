package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.presenter.interfaces.IHandPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class HandPresenter implements IHandPresenter {
    public interface View {
        void updatePlayerHandDisplay(List<TrainCard> cards);
    }
}
