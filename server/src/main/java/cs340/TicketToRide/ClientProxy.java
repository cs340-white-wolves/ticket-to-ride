package cs340.TicketToRide;

import java.util.List;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.model.ClientCommandQueue;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;

public class ClientProxy implements IClient {
    public ClientCommandQueue queue = new ClientCommandQueue();

    public ClientCommandQueue getQueue() {
        return queue;
    }

    @Override
    public void chatMessageReceived(ChatMessage message) {

        Command gotChat = new Command(
                "chatMessageReceived",
                new String[]{ChatMessage.class.getName()},
                new Object[]{message}
        );

        queue.add(gotChat);
    }

    @Override
    public void playersUpdated(Players players) {
        Command playersUpdated = new Command(
          "playersUpdated",
          new String[]{players.getClass().getName()},
          new Object[]{players}
        );
        queue.add(playersUpdated);
    }

    @Override
    public void destCardDeckChanged(DestinationCards destCardDeck) {
        Command destCardDeckChanged = new Command(
                "destCardDeckChanged",
                new String[]{DestinationCards.class.getName()},
                new Object[]{destCardDeck}
        );
        queue.add(destCardDeckChanged);
    }

    @Override
    public void startGame() {
        Command gameStarted = new Command(
                "startGame",
                new String[]{},
                new Object[]{}
        );
        queue.add(gameStarted);
    }
}
