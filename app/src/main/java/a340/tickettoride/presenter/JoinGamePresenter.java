package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.task.JoinGameTask;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;

public class JoinGamePresenter implements IJoinGamePresenter {
    private View view;

    public JoinGamePresenter(View view) {
        this.view = view;
    }

    @Override
    public void joinGame(ID gameID) {
        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        ServiceFacade.getInstance().joinGame(gameID);
    }

    public interface View {
        void onGameJoined();
    }
}
