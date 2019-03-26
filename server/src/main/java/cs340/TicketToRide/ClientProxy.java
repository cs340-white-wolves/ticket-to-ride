package cs340.TicketToRide;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.model.ClientCommandQueue;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCards;

public class ClientProxy implements IClient {
    public ClientCommandQueue queue = new ClientCommandQueue();

    public ClientCommandQueue getQueue() {
        return queue;
    }

    @Override
    public void chatMessageReceived(Message message) {

        Command gotChat = new Command(
                "chatMessageReceived",
                new String[]{Message.class.getName()},
                new Object[]{message}
        );

        queue.add(gotChat);
    }

    @Override
    public void historyMessageReceived(Message message) {
        Command gotHistory = new Command(
                "historyMessageReceived",
                new String[]{Message.class.getName()},
                new Object[]{message}
        );
        queue.add(gotHistory);
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

    @Override
    public void faceUpDeckChanged(TrainCards trainCards) {
        Command changeFaceUpDeck = new Command(
                "faceUpDeckChanged",
                new String[]{TrainCards.class.getName()},
                new Object[]{trainCards}
        );
        queue.add(changeFaceUpDeck);
    }

    @Override
    public void trainCardDeckChanged(TrainCards trainCards) {
        Command changeTrainCardDeck = new Command(
                "trainCardDeckChanged",
                new String[]{TrainCards.class.getName()},
                new Object[]{trainCards}
        );
        queue.add(changeTrainCardDeck);
    }

    @Override
    public void routeUpdated(Route route) {
        Command updateRoute = new Command(
                "routeUpdated",
                new String[]{Route.class.getName()},
                new Object[]{route}
        );
        queue.add(updateRoute);
    }

    @Override
    public void addedDestCards(DestinationCards cardsToAddToPlayer) {
        Command command = new Command(
                "addedDestCards",
                new String[]{DestinationCards.class.getName()},
                new Object[]{cardsToAddToPlayer}
        );
        queue.add(command);
    }

    @Override
    public void advanceTurn() {
        Command command = new Command(
                "advanceTurn",
                new String[]{},
                new Object[]{}
        );
        queue.add(command);
    }
}
