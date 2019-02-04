package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

/**
 * This calls the Service facade and also updates the Views
 */
public class MainPresenter implements IMainPresenter {
    private Username username;
    private Password password;


    public void login(String usernameStr, String passStr) throws Exception {
        setUsernamePassword(usernameStr, passStr);
        ServiceFacade facade = ServiceFacade.getInstance();
        facade.login(username, password);
    }

    public void register(String usernameStr, String passStr) throws Exception {
        setUsernamePassword(usernameStr, passStr);

        ServiceFacade facade = ServiceFacade.getInstance();
        facade.register(username, password);
    }

    private void setUsernamePassword(String usernameStr, String passStr) throws Exception {
        if (usernameStr == null || passStr == null || usernameStr.equals("") || passStr.equals("")) {
            throw new IllegalArgumentException();
        }

        username = new Username(usernameStr);
        password = new Password(passStr);

        if (!username.isValid()) {
            throw new Exception("Invalid Username");
        }
        if (!password.isValid()) {
            throw new Exception("Invalid Password");
        }
    }
}
