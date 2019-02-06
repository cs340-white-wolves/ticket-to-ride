package a340.tickettoride.task;

import android.os.AsyncTask;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;

public class CreateGameTask extends AsyncTask<Void, Integer, Game> {
    private IClientModel model = ClientModel.getInstance();
    private int numPlayers;
    private Exception exception;

    public CreateGameTask(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    protected Game doInBackground(Void... voids) {
        IServer server = ServerProxy.getInstance();
        AuthToken token = model.getAuthToken();
        try {
            return server.createGame(token, numPlayers);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Game game) {
        IClientModel model = ClientModel.getInstance();
        if (game == null) {

        }


    }
}
