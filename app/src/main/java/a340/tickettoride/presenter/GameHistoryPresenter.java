package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IChatPresenter;
import a340.tickettoride.presenter.interfaces.IGameHistoryPresenter;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.utility.Username;

public class GameHistoryPresenter implements IGameHistoryPresenter, ModelObserver {
    private View listener;

    private IClientModel model = ClientModel.getInstance();

    public GameHistoryPresenter(View listener) {
        this.listener = listener;
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
        if (changeType == ModelChangeType.GameHistoryReceived) {
            // TODO: stuff
        }
    }

    public interface View {
        // TODO: stuff
    }

}