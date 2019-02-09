package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.task.CreateGameTask;
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
    public void update(Observable o, Object arg) {
        // Nothing to observe right now
    }

    public interface View {
        void onGameCreated();
    }
}
