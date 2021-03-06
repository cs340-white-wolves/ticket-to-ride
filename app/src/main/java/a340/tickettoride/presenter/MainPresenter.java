package a340.tickettoride.presenter;

import android.util.Log;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IMainPresenter;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

/**
 * This calls the Service facade and also updates the Views
 */
public class MainPresenter implements ModelObserver, IMainPresenter {
    private Username username;
    private Password password;
    private View view;

    public MainPresenter(View view) {
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
        try {
            setUsernamePassword(usernameStr, passStr);
            ServiceFacade.getInstance().login(username, password);
        } catch (IllegalArgumentException e) {
            view.onInvalid(e.getMessage());
        }

    }

    @Override
    public void register(String usernameStr, String passStr) {
        Log.d("MainPresenter", "in register");
        try {
            setUsernamePassword(usernameStr, passStr);
            ServiceFacade.getInstance().register(username, password);
        } catch (IllegalArgumentException e) {
            view.onInvalid(e.getMessage());
        }

    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.AuthenticateSuccess) {
            view.onAuthenticated();
            return;
        }
        else if (changeType == ModelChangeType.FailureException) {
            Exception exception = (Exception)obj;
            view.onInvalid(exception.getMessage());
            return;
        }
    }

    private void setUsernamePassword(String usernameStr, String passStr) {
        if (usernameStr == null || passStr == null || usernameStr.equals("") || passStr.equals("")) {
            throw new IllegalArgumentException();
        }

        CharsetEncoder charsetEncoder = Charset.forName("US-ASCII").newEncoder();
        if (!charsetEncoder.canEncode(usernameStr) || !charsetEncoder.canEncode(passStr)) {
            throw new IllegalArgumentException("Invalid Input");
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
