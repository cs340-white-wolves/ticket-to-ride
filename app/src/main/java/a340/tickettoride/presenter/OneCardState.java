package a340.tickettoride.presenter;

import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class OneCardState implements ITrainCardState {

    private static OneCardState state = new OneCardState();
    public static OneCardState getInstance() {return state;}

    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, int index, TrainCard.Color color) {
        presenter.setState(FinalState.getInstance());
    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, int index) throws InvalidMoveException {
        throw new InvalidMoveException("Cannot pick up locomotive card");
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) {
        presenter.setState(FinalState.getInstance());
    }

    @Override
    public void finishTurn() {

    }
}
