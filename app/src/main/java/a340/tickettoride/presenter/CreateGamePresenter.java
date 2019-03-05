package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.ICreateGamePresenter;
import cs340.TicketToRide.model.game.Game;

public class CreateGamePresenter implements ICreateGamePresenter, ModelObserver {
    private View view;

    public CreateGamePresenter(View view) {
        this.view = view;
    }

    @Override
    public void createGame(int numPlayers) {
        if (numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        ServiceFacade.getInstance().createGame(numPlayers);
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
        if (changeType == ModelChangeType.JoinGame) {
            view.onGameCreated();
            return;
        }

        if (changeType == ModelChangeType.FailureException) {
            Exception exception = (Exception)obj;
            view.onInvalid(exception.getMessage());
        }
    }

    public interface View {
        void onGameCreated();
        void onInvalid(String errorMsg);
    }
}
