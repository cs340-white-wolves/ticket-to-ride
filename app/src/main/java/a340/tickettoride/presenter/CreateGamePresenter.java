package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.task.CreateGameTask;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.GameFullException;
import cs340.TicketToRide.model.Game;

public class CreateGamePresenter implements ICreateGamePresenter, Observer {
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
    public void update(Observable o, Object arg) {
        if (arg instanceof AuthenticationException) {
            Exception exception = (Exception)arg;
            view.onInvalid(exception.getMessage());
            return;
        }

        // todo: how to handle?
        if (arg instanceof Exception) {
            Exception exception = (Exception)arg;
            view.onInvalid(exception.getMessage());
            return;
        }

        if (arg instanceof Game) {
            view.onGameCreated();
        }

        // todo: unknown error
    }

    public interface View {
        void onGameCreated();
        void onInvalid(String errorMsg);
    }
}
