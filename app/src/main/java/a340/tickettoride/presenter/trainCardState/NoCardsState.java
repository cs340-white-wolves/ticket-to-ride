package a340.tickettoride.presenter.trainCardState;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class NoCardsState implements ITrainCardState {

    private static NoCardsState state = new NoCardsState();
    public static NoCardsState getInstance() {return state;}

    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, TrainCard card) {
        presenter.setState(DisabledState.getInstance());
        ClientModel.getInstance().onSelectedSingleCard();
        ServiceFacade.getInstance().drawTrainCard(card, false);

    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, TrainCard card) {
        presenter.setState(FinalState.getInstance());
        ServiceFacade.getInstance().drawTrainCard(card, true);
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) {
        presenter.setState(DisabledState.getInstance());
        ClientModel.getInstance().onSelectedSingleCard();
        ServiceFacade.getInstance().drawTrainCard(presenter.getTopOfDeck(), false);

    }

    @Override
    public void bankUpdated(IBankPresenter presenter) {

    }

}
