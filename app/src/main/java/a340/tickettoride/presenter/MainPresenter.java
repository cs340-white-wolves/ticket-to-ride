package a340.tickettoride.presenter;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.task.LoginTask;
import a340.tickettoride.task.RegisterTask;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.NotUniqueException;
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
        ClientModel.getInstance().setupNewObserver(this);
        this.view = view;
    }

    @Override
    public void startObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void stopObserving() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void login(String usernameStr, String passStr) {
        setUsernamePassword(usernameStr, passStr);
        ServiceFacade.getInstance().login(username, password);
    }

    @Override
    public void register(String usernameStr, String passStr) {
        Log.d("MainPresenter", "in register");
        setUsernamePassword(usernameStr, passStr);
        ServiceFacade.getInstance().register(username, password);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof LoginRegisterResponse) {
            view.onAuthenticated();
            return;
        }

        // todo: how do we want to handle the diff cases?
        if (arg instanceof NotUniqueException || arg instanceof AuthenticationException) {
            Exception exception = (Exception)arg;
            view.onInvalid(exception.getMessage());
            return;
        }

        if (arg instanceof Exception) {
            Exception exception = (Exception)arg;
            view.onInvalid(exception.getMessage());
            return;
        }

        // todo: this is getting called because other presenters are updating also
//        view.onInvalid("Unknown Error");
    }

    private void setUsernamePassword(String usernameStr, String passStr) {
        if (usernameStr == null || passStr == null || usernameStr.equals("") || passStr.equals("")) {
            throw new IllegalArgumentException();
        }

        username = new Username(usernameStr);
        password = new Password(passStr);

        if (!username.isValid()) {
            throw new RuntimeException("Invalid Username");
        }
        if (!password.isValid()) {
            throw new RuntimeException("Invalid Password");
        }
    }


    public interface View {
        public void onAuthenticated();
        public void onInvalid(String errorMessage); // shows up as a textview
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
}
