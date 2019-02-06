package a340.tickettoride.presenter;

import a340.tickettoride.task.JoinGameTask;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;

public class JoinGamePresenter implements IJoinGamePresenter {
    private View view;

    public JoinGamePresenter(View view) {
        setView(view);
    }

    @Override
    public void joinGame(ID gameID) {
        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        JoinGameTask task = new JoinGameTask(gameID);
        task.execute();
    }

    public interface View {
        void gameJoined();
    }

    public void setView(View view) {
        this.view = view;
    }
}
