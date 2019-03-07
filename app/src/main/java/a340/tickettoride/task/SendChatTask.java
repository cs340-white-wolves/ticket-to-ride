package a340.tickettoride.task;

import android.os.AsyncTask;

import java.util.List;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.utility.ID;

public class SendChatTask extends AsyncTask<Void, Integer, Void> {

    private IClientModel model = ClientModel.getInstance();
    private Exception exception;
    private ChatMessage message;

    public SendChatTask(ChatMessage message) {
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        IServer server = ServerProxy.getInstance();
        AuthToken token = model.getAuthToken();
        ID gameID = model.getActiveGame().getGameID();

        try {
            server.sendChat(token, gameID, message);
        } catch (Exception e) {
            exception = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        if (exception != null) {
            model.onSendChatFail(exception);
        }

    }

}
