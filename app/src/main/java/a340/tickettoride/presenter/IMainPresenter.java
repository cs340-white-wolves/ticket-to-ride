package a340.tickettoride.presenter;

public interface IMainPresenter {
    void login(String usernameStr, String passwordStr) throws Exception;
    void register(String usernamtStr, String passwordStr) throws Exception;
}
