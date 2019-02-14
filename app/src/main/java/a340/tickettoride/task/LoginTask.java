package a340.tickettoride.task;

import android.os.AsyncTask;
import android.util.Log;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class LoginTask extends AsyncTask<Void, Integer, LoginRegisterResponse> {

    private Username username;
    private Password password;
    private Exception exception;

    public LoginTask(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected LoginRegisterResponse doInBackground(Void... voids) {
        IServer server = ServerProxy.getInstance();
        try {
            return server.login(username, password);
        } catch (Exception e) {
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(LoginRegisterResponse response) {
        IClientModel model = ClientModel.getInstance();

        if (response == null) {
            model.onAuthenticateFail(exception);
            return;
        }

        model.onAuthenticateSuccess(response);
    }
}
