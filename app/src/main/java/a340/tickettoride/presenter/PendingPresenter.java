package a340.tickettoride.presenter;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import a340.tickettoride.model.ClientModel;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.utility.ID;

public class PendingPresenter implements IPendingPresenter, Observer {
    private View view;

    public PendingPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
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
        Log.i("PendingPresenter", "Got update" + arg.getClass().getName());
        // TODO: right now, this is using the entire game list. We should switch to only polling the active game.
        if (arg instanceof Games) {
            Game activeGame = ClientModel.getInstance().getActiveGame();

            view.onUpdateGame(activeGame);
            view.onUpdatePlayers(activeGame.getPlayers());

            if (activeGame.hasTargetNumPlayers()) {
                view.onGameStarting();
            }
        }
    }

    @Override
    public Game getActiveGame() {
        return ClientModel.getInstance().getActiveGame();
    }

    public interface View {
        void onUpdatePlayers(Set<Player> players);
        void onUpdateGame(Game game);
        void onGameStarting();
    }
}
