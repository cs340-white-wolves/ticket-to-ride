package a340.tickettoride.presenter.trainCardState;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public class NoCardsState implements ITrainCardState {

    private static NoCardsState state = new NoCardsState();
    public static NoCardsState getInstance() {return state;}

    @Override
    public void drawStandardFaceUp(IBankPresenter presenter, TrainCard card) {
        boolean advanceTurn = false;

        TrainCards currentFaceUpCards = new TrainCards();
        currentFaceUpCards.addAll(presenter.getCurrentFaceUpCards());
        currentFaceUpCards.remove(card);

        if (presenter.getNumTrainCards() == 0 && (currentFaceUpCards.size() == 1
                || allFaceupsLocomotives(currentFaceUpCards))) {
            advanceTurn = true;
            presenter.setState(FinalState.getInstance());
        } else {
            presenter.setState(DisabledState.getInstance());
        }

        ClientModel.getInstance().onSelectedSingleCard();
        ServiceFacade.getInstance().drawTrainCard(card, advanceTurn);

    }

    @Override
    public void drawLocomotiveFaceUp(IBankPresenter presenter, TrainCard card) {
        presenter.setState(FinalState.getInstance());
        ServiceFacade.getInstance().drawTrainCard(card, true);
    }

    @Override
    public void drawFromDeck(IBankPresenter presenter) throws InvalidMoveException {
        boolean advanceTurn = false;
        TrainCards currentFaceUpCards = presenter.getCurrentFaceUpCards();
        int numFaceUp = currentFaceUpCards.size();
        int numDeckTrainCards = presenter.getNumTrainCards();

        if (numDeckTrainCards == 0) {
            throw new InvalidMoveException("There are no cards in deck");
        } else if (numDeckTrainCards == 1 && (numFaceUp == 0 || allFaceupsLocomotives(currentFaceUpCards))){
            advanceTurn = true;
            presenter.setState(FinalState.getInstance());
        } else {
            presenter.setState(DisabledState.getInstance());
        }

        ClientModel.getInstance().onSelectedSingleCard();
        TrainCard topCard = presenter.getTopOfDeck();
        ServiceFacade.getInstance().drawTrainCard(topCard, advanceTurn);
    }

    private boolean allFaceupsLocomotives(TrainCards currentFaceUpCards) {
        int numFaceUpLocomotives = 0;
        for (TrainCard faceup : currentFaceUpCards) {
            if (faceup.getColor() == TrainCard.Color.locomotive) {
                numFaceUpLocomotives++;
            }
        }
        return numFaceUpLocomotives == currentFaceUpCards.size();
    }

    @Override
    public void bankUpdated(IBankPresenter presenter) {

    }

}
