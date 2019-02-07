package a340.tickettoride.presenter;

public class LobbyPresenter implements ILobbyPresenter {
    private View view;

    public LobbyPresenter(View view) {
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

    public interface View {
        void onPressCreateGame();
        void onPressJoinGame();
    }
}
