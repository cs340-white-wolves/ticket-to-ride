package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IPendingPresenter;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;

public class PendingPresenter implements IPendingPresenter, ModelObserver {
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
    public Game getActiveGame() {
        return ClientModel.getInstance().getActiveGame();
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        // TODO: right now, this is using the entire game list. We should switch to only polling the active game.
        if (changeType == ModelChangeType.AvailableGameList) {
            Game activeGame = ClientModel.getInstance().getActiveGame();

            view.onUpdateGame(activeGame);
            view.onUpdatePlayers(activeGame.getPlayers());
        }

        if (changeType == ModelChangeType.StartMap) {
            view.onGameStarting();
        }
    }

    public interface View {
        void onUpdatePlayers(Players players);
        void onUpdateGame(Game game);
        void onGameStarting();
    }
}
