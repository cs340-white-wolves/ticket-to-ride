package a340.tickettoride.presenter;

import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class FinalState implements ITrainCardState {

    private static FinalState state = new FinalState();
    private static final String MESSAGE = "Cannot draw cards";

    public static FinalState getInstance() {return state;}

    private FinalState() {

    }
    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, TrainCard card) throws InvalidMoveException {
        throw new InvalidMoveException(MESSAGE);
    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, TrainCard card) throws InvalidMoveException {
        throw new InvalidMoveException(MESSAGE);
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) throws InvalidMoveException {
        throw new InvalidMoveException(MESSAGE);
    }

}
