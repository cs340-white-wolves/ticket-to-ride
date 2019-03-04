package a340.tickettoride;

import android.util.Log;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.game.ChatMessage;

public class ClientFacade implements IClient {
    private static ClientFacade singleton;

    private ClientFacade() {
    }

    public static ClientFacade getInstance() {
        if (singleton == null) {
            singleton = new ClientFacade();
        }
        return singleton;
    }

    @Override
    public void gotChat(ChatMessage message) {
        Log.i("ClientFacade", "Got chat message!");
    }

}
