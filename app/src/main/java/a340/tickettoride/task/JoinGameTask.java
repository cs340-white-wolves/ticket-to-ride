package a340.tickettoride.task;

import android.os.AsyncTask;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.utility.ID;

public class JoinGameTask extends AsyncTask<Void, Integer, Boolean> {

    private IClientModel model = ClientModel.getInstance();
    private ID gameID;
    private Exception exception;

    public JoinGameTask(ID gameID) {
        this.gameID = gameID;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        IServer server = ServerProxy.getInstance();
        AuthToken token = model.getAuthToken();

        try {
            return server.joinGame(token, gameID);
        } catch (Exception e) {
            this.exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean joinGameSuccess) {
        if (joinGameSuccess) {
            model.onJoinGameSuccess();
            return;
        }

        model.onJoinGameFail(exception);
    }
}
