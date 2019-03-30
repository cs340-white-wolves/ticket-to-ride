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
        boolean advanceTurn = false;
        if (presenter.getNumTrainCards() <= 1) {
            presenter.setState(FinalState.getInstance());
            advanceTurn = true;
        } else {
            presenter.setState(DisabledState.getInstance());
        }
        ClientModel.getInstance().onSelectedSingleCard();
        TrainCard topCard = presenter.getTopOfDeck();
        ServiceFacade.getInstance().drawTrainCard(topCard, advanceTurn);
    }

    @Override
    public void bankUpdated(IBankPresenter presenter) {

    }

}
