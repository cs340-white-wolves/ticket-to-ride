package cs340.TicketToRide;

import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCards;

public interface IClient {
    void chatMessageReceived(Message message);
    void playersUpdated(Players players);
    void destCardDeckChanged(DestinationCards destCardDeck);
    void startGame();
    void faceUpDeckChanged(TrainCards trainCards);
    void trainCardDeckChanged(TrainCards trainCards);
    void routeUpdated(Route route);
}
