package a340.tickettoride.presenter.interfaces;

public interface ICreateGamePresenter {
    void createGame(int numPlayers);
    void startObserving();
    void stopObserving();
}
