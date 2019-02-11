package a340.tickettoride.presenter;

public interface IMainPresenter {
    void login(String usernameStr, String passwordStr);
    void register(String usernamtStr, String passwordStr);
    void startObserving();
    void stopObserving();
}
