package a340.tickettoride.presenter.interfaces;

import java.util.List;

import a340.tickettoride.model.IClientModel;
import a340.tickettoride.presenter.ITrainCardState;
import a340.tickettoride.presenter.InvalidMoveException;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public interface IBankPresenter {

    TrainCards getCurrentFaceUpCards();
    void drawStandardFaceUp(int index, TrainCard.Color color) throws InvalidMoveException;
    void drawLocomotiveFaceUp(int index) throws InvalidMoveException;
    void drawFromDeck() throws InvalidMoveException;
    int getNumTrainCards();
    int getNumDestCards();
    void startObserving();
    void stopObserving();
    void setState(ITrainCardState state);
    IClientModel getModel();

}
