package a340.tickettoride.presenter;

public interface IMainPresenter {
    void login(String username, String password) throws Exception;
    void register(String usr, String pass) throws Exception;
}
