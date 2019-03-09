package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IHandPresenter;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public class HandPresenter implements IHandPresenter, ModelObserver {

    private View view;

    public HandPresenter(View view) {
        this.view = view;
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.UpdatePlayerHand) {
            Player player = (Player) obj;
            view.updatePlayerHandDisplay(player.getTrainCards());
        }
    }

    @Override
    public void startObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void stopObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    public interface View {
        void updatePlayerHandDisplay(TrainCards cards);
    }
}
