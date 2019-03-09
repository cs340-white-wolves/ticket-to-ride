package a340.tickettoride.presenter;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IPlayerInfoPresenter;
import cs340.TicketToRide.model.game.Players;

public class PlayerInfoPresenter implements IPlayerInfoPresenter, ModelObserver {
    private View view;
    private ClientModel model;

    public PlayerInfoPresenter(View view) {
        this.view = view;
        model = ClientModel.getInstance();
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
    public Players getPlayers() {
        return model.getActiveGame().getPlayers();
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.UpdatePlayers) {
            Players players = (Players) obj;
            view.updateAllPlayers(players);
        }
    }

    public interface View {
        void updateAllPlayers(Players players);
    }
}
