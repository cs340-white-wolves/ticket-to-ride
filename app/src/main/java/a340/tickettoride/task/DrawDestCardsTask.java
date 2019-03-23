package a340.tickettoride.task;

import android.os.AsyncTask;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.utility.ID;

public class DrawDestCardsTask extends AsyncTask<Void, Integer, Void> {

    private IClientModel model = ClientModel.getInstance();
    private Exception exception;

    @Override
    protected Void doInBackground(Void... voids) {
        IServer server = ServerProxy.getInstance();
        AuthToken token = model.getAuthToken();
        ID gameID = model.getActiveGame().getGameID();
        ID playerId = model.getPlayerId();

        try {
            server.drawDestCards(token, gameID, playerId);
        } catch (Exception e) {
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if (exception != null) {
            model.onDiscardDestCardsFail(exception);
        }

    }

}
