package a340.tickettoride.presenter.trainCardState;

import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

import static a340.tickettoride.presenter.trainCardState.FinalState.MESSAGE;

public class DisabledState implements ITrainCardState {

    private static DisabledState state = new DisabledState();
    public static DisabledState getInstance() {return state;}

    private DisabledState() {}

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

    @Override
    public void bankUpdated(IBankPresenter presenter) {
        presenter.setState(OneCardState.getInstance());
    }
}
