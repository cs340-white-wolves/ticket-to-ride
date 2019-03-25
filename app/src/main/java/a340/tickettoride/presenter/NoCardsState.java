package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class NoCardsState implements ITrainCardState {

    private static NoCardsState state = new NoCardsState();
    public static NoCardsState getInstance() {return state;}

    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, TrainCard card) {
        presenter.setState(OneCardState.getInstance());
        ServiceFacade.getInstance().drawTrainCard(card);

    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, TrainCard card) {
        presenter.setState(FinalState.getInstance());
        ServiceFacade.getInstance().drawTrainCard(card);
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) {
        presenter.setState(OneCardState.getInstance());
        ServiceFacade.getInstance().drawTrainCard(presenter.getTopOfDeck());

    }

}
