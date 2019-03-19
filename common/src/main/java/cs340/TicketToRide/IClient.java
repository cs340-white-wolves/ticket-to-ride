package cs340.TicketToRide;

import java.util.List;

import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCards;

public interface IClient {
    void chatMessageReceived(ChatMessage message);
    void playersUpdated(Players players);
    void destCardDeckChanged(DestinationCards destCardDeck);
    void startGame();
    void faceUpDeckChanged(TrainCards trainCards);
    void trainCardDeckChanged(TrainCards trainCards);
}
