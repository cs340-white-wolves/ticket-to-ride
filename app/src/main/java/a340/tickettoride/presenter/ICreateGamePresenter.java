package a340.tickettoride.presenter;

public interface ICreateGamePresenter {
    void createGame(int numPlayers);
    void startObserving();
    void stopObserving();
}
