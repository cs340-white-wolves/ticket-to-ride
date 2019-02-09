package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.model.ClientModel;

public class LobbyPresenter implements ILobbyPresenter, Observer {
    private View view;

    public LobbyPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
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
    public void update(Observable o, Object arg) {

    }

    public interface View {
        void onPressCreateGame();
        void onPressJoinGame();
    }
}
