package a340.tickettoride.presenter;

import java.util.Set;

import cs340.TicketToRide.model.Player;

public class PendingPresenter implements IPendingPresenter {
    private View view;

    public PendingPresenter(View view) {
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

    public interface View {
        void onUpdatePlayers(Set<Player> players);
        void onGameStarting();
    }
}
