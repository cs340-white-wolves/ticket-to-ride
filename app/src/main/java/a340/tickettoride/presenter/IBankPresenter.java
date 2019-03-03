package a340.tickettoride.presenter;

import java.util.List;

import cs340.TicketToRide.model.game.card.TrainCard;

public interface IBankPresenter {

    //Get all the current face up cards for initializtion
    List<TrainCard> getCurrentFaceUpCards();

    //Return a train card that is drawn off of the face down deck
    TrainCard drawTrainCard();

    //Pick up a train card and replace it with one from the deck
    TrainCard pickUpTrainCard(int index);

}
