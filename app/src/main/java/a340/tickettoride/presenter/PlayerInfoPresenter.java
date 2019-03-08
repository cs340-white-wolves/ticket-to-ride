package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IPlayerInfoPresenter;
import cs340.TicketToRide.model.game.Player;

public class PlayerInfoPresenter implements IPlayerInfoPresenter, ModelObserver {
    private View view;

    public PlayerInfoPresenter(View view) {
        this.view = view;
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.UpdatePlayers) {
            List<Player> players = (List<Player>) obj;
            view.updateAllPlayers(players);
        }
    }

    public interface View {
        void updateAllPlayers(List<Player> players);
    }
}
