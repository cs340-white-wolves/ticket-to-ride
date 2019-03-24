package a340.tickettoride.presenter;

import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public interface ITrainCardState {
    void drawStandardFaceUp(IBankPresenter presenter, TrainCard card) throws InvalidMoveException;
    void drawLocomotiveFaceUp(IBankPresenter presenter, TrainCard card) throws InvalidMoveException;
    void drawFromDeck(IBankPresenter presenter) throws InvalidMoveException;
}
