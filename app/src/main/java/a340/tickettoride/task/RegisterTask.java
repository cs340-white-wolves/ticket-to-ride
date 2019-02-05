package a340.tickettoride.task;

import android.os.AsyncTask;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class RegisterTask extends AsyncTask<Object, Integer, LoginRegisterResponse> {
    private Username username;
    private Password password;
    private Exception exception;

    public RegisterTask(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected LoginRegisterResponse doInBackground(Object... objects) {
        if (objects.length != 0) {
            return null;
        }

        IServer server = ServerProxy.getInstance();
        try {
            return server.register(username, password);
        } catch (Exception e) {
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(LoginRegisterResponse response) {
        ClientModel model = ClientModel.getInstance();
        if (response != null) {
            model.onAuthenticateSuccess(response);
            return;
        }

        model.onAuthenticateFail(exception);
    }

}
