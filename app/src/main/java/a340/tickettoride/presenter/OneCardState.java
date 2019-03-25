package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class OneCardState implements ITrainCardState {

    private static OneCardState state = new OneCardState();
    public static OneCardState getInstance() {return state;}

    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, TrainCard card) {
        presenter.setState(FinalState.getInstance());
        ServiceFacade.getInstance().drawTrainCard(card);
    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, TrainCard card) throws InvalidMoveException {
        throw new InvalidMoveException("Cannot pick up locomotive card");
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) {
        presenter.setState(FinalState.getInstance());
    }

}
