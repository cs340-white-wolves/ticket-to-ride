package a340.tickettoride.presenter;

import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class FinalState implements ITrainCardState {

    private static FinalState state = new FinalState();
    public static FinalState getInstance() {return state;}

    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, TrainCard card) throws InvalidMoveException {
        throw new InvalidMoveException("Cannot draw more cards");
    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, TrainCard card) throws InvalidMoveException {
        throw new InvalidMoveException("Cannot draw more cards");
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) throws InvalidMoveException {
        throw new InvalidMoveException("Cannot draw more cards");
    }

}
