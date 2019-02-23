package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;

public class LobbyPresenter implements ILobbyPresenter, ModelObserver {
    private View view;

    public LobbyPresenter(View view) {
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
    public void onPressCreateGame() {
        view.onPressCreateGame();
    }

    @Override
    public void onPressJoinGame() {
        view.onPressJoinGame();
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        // This does not do anything currently
    }

    public interface View {
        void onPressCreateGame();
        void onPressJoinGame();
    }
}
