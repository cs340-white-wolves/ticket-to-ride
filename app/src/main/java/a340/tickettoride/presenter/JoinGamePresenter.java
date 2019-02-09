package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.task.JoinGameTask;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.GameFullException;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;

public class JoinGamePresenter implements IJoinGamePresenter, Observer {
    private View view;

    public JoinGamePresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public void joinGame(ID gameID) {
        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        ServiceFacade.getInstance().joinGame(gameID);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof GameFullException || arg instanceof AuthenticationException) {
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
            view.onGameJoined();
        }

        // todo: unknown error
    }

    public interface View {
        void onGameJoined();
        void onInvalid(String errorMsg);
    }
}
