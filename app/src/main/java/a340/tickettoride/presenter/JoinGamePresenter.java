package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.task.JoinGameTask;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
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
        if (arg instanceof Games) {
            view.onGameListUpdate((Games) arg);
            return;
        }

        if (arg instanceof Exception) {
            Exception e = (Exception) arg;
            view.onGameJoinFail(e.getMessage());
        }
    }

    public interface View {
        void onGameJoined();
        void onGameJoinFail(String msg);
        void onGameListUpdate(Games games);
    }
}
