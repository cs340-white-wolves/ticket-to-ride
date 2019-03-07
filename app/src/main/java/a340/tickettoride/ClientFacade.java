package a340.tickettoride;

import android.util.Log;

import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;

public class ClientFacade implements IClient {
    private static ClientFacade singleton;
    private IClientModel model = ClientModel.getInstance();

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
        model.onChatMessageReceived(message);
        Log.i("ClientFacade", "Got chat message!");
    }

    @Override
    public void playersUpdated(List<Player> players) {
        model.updatePlayers(players);
    }

    @Override
    public void destCardDeckChanged(Deck<DestinationCard> destCardDeck) {
        model.updateGameDestCardDeck(destCardDeck);
    }

}
