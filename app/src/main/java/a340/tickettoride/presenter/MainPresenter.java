package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.task.RegisterTask;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

/**
 * This calls the Service facade and also updates the Views
 */
public class MainPresenter implements Observer, IMainPresenter{
    private Username username;
    private Password password;

    @Override
    public void login(String usernameStr, String passStr) throws Exception {
        setUsernamePassword(usernameStr, passStr);
        ServiceFacade facade = ServiceFacade.getInstance();
        facade.login(username, password);
    }

    @Override
    public void register(String usernameStr, String passStr) throws Exception {
        setUsernamePassword(usernameStr, passStr);

        ServiceFacade facade = ServiceFacade.getInstance();
        facade.register(username, password);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof LoginRegisterResponse) {
            LoginRegisterResponse response = (LoginRegisterResponse)arg;
            // todo: success
            return;
        }

        if (arg instanceof Exception) {
            // todo: fail
            return;
        }

        // todo: unknown, failure
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
