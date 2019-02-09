package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import a340.tickettoride.model.ClientModel;
import cs340.TicketToRide.model.Player;

public class PendingPresenter implements IPendingPresenter, Observer {
    private View view;

    public PendingPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public String getGameName() {
        // return the name of the active game

        return null;
    }

    @Override
    public Set<Player> getPlayers() {
        // return the set of players (usernames) in the active game

        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public interface View {
        void onUpdatePlayers(Set<Player> players);
        void onGameStarting();
    }
}
