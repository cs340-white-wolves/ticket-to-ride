package a340.tickettoride;

import android.util.Log;

import java.util.List;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;

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
    public void chatMessageReceived(ChatMessage message) {
        Log.i("ClientFacade", "Got chat message!");
    }

    @Override
    public void playersUpdated(List<Player> players) {

    }

}
