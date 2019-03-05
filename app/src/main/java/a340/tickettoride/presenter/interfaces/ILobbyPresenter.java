package a340.tickettoride.presenter.interfaces;

public interface ILobbyPresenter {
    void onPressCreateGame();
    void onPressJoinGame();
    void startObserving();
    void stopObserving();
}
