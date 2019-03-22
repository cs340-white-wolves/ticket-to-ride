package a340.tickettoride.presenter;

import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class NoCardsState implements ITrainCardState {

    private static NoCardsState state = new NoCardsState();
    public static NoCardsState getInstance() {return state;}

    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, int index, TrainCard.Color color) {
        presenter.setState(OneCardState.getInstance());
        presenter.getModel();
    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, int index) {
        presenter.setState(FinalState.getInstance());
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) {
        presenter.setState(OneCardState.getInstance());

    }

    @Override
    public void finishTurn() {

    }
}
