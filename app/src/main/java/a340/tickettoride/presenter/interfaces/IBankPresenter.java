package a340.tickettoride.presenter.interfaces;

import a340.tickettoride.presenter.ITrainCardState;
import a340.tickettoride.presenter.InvalidMoveException;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public interface IBankPresenter {

    TrainCards getCurrentFaceUpCards();
    void drawStandardFaceUp(TrainCard card) throws InvalidMoveException;
    void drawLocomotiveFaceUp(TrainCard card) throws InvalidMoveException;
    void drawFromDeck() throws InvalidMoveException;
    int getNumTrainCards();
    int getNumDestCards();
    void startObserving();
    void stopObserving();
    void setState(ITrainCardState state);
    TrainCard getTopOfDeck();

}
