package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.task.LoginTask;
import a340.tickettoride.task.RegisterTask;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

/**
 * This calls the Service facade and also updates the Views
 */
public class MainPresenter implements Observer, IMainPresenter {
    private Username username;
    private Password password;
    private View view;

    public MainPresenter(View view) {
        setView(view);
    }

    @Override
    public void login(String usernameStr, String passStr) throws Exception {
        setUsernamePassword(usernameStr, passStr);
        LoginTask task = new LoginTask(username, password);
        task.execute();
    }

    @Override
    public void register(String usernameStr, String passStr) throws Exception {
        setUsernamePassword(usernameStr, passStr);
        RegisterTask task = new RegisterTask(username, password);
        task.execute();
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


    public interface View {
        public void loggedIn();
        public void invalid(String errorMessage); // shows up as a toast
    }


    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
