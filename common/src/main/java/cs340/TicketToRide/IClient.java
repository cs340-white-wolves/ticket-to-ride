package cs340.TicketToRide;

import java.util.List;

import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;

public interface IClient {
    void chatMessageReceived(ChatMessage message);
    void playersUpdated(List<Player> players);

    void destCardDeckChanged(Deck<DestinationCard> destCardDeck);
}
