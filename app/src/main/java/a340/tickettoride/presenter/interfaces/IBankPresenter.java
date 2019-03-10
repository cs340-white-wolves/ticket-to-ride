package a340.tickettoride.presenter.interfaces;

import java.util.List;

import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public interface IBankPresenter {

    TrainCards getCurrentFaceUpCards();
    TrainCard drawTrainCard();
    TrainCard pickUpTrainCard(int index);
    int getNumTrainCards();
    int getNumDestCards();
    void startObserving();
    void stopObserving();

}
