package a340.tickettoride.presenter;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import a340.tickettoride.presenter.trainCardState.FinalState;
import a340.tickettoride.presenter.trainCardState.ITrainCardState;
import a340.tickettoride.presenter.trainCardState.InvalidMoveException;
import a340.tickettoride.presenter.trainCardState.NoCardsState;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public class BankPresenter implements IBankPresenter, ModelObserver {

    public static final String NOT_TURN_MSG = "It's not your turn";
    private View view;
    private IClientModel model = ClientModel.getInstance();
    private ITrainCardState state = FinalState.getInstance();

    public BankPresenter(View view) {
        this.view = view;
    }

    @Override
    public TrainCards getCurrentFaceUpCards() {

        return model.getActiveGame().getFaceUpTrainCards();
    }

    @Override
    public int getNumTrainCards() { return model.getActiveGame().getTrainCardDeck().size(); }

    @Override
    public int getNumDestCards() {
        return model.getActiveGame().getDestCardDeck().size();
    }


    //==============================State Methods==============================

    private void checkIfPlayersTurn() throws InvalidMoveException {
        if (!model.activePlayerTurn()) {
            throw new InvalidMoveException(NOT_TURN_MSG);
        }
    }
    @Override
    public void drawStandardFaceUp(TrainCard card) throws InvalidMoveException {
        checkIfPlayersTurn();
        state.drawStandardFaceUp(this, card);
    }

    @Override
    public void drawLocomotiveFaceUp(TrainCard card) throws InvalidMoveException {
        checkIfPlayersTurn();
        state.drawLocomotiveFaceUp(this, card);
    }

    @Override
    public void drawFromDeck() throws InvalidMoveException {
        checkIfPlayersTurn();
        state.drawFromDeck(this);
    }

    @Override
    public void setState(ITrainCardState state) {
        this.state = state;
    }

    @Override
    public TrainCard getTopOfDeck() {
        TrainCards deck = model.getActiveGame().getTrainCardDeck();

        if (deck.size() != 0) {
            return deck.get(0);
        }
        return null;
    }


    //=============================Model Methods=================================
    @Override
    public void startObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void stopObserving() {
        ClientModel.getInstance().deleteObserver(this);
    }


    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.DrawableDestinationCardCount) {
            int count = (int) obj;
            view.updateDestinationCardCount(count);

        } else if (changeType == ModelChangeType.DrawableTrainCardCount) {
            int count = (int) obj;
            view.updateDrawableTrainCardCount(count);

        } else if (changeType == ModelChangeType.FaceUpTrainCardsUpdate) {
            TrainCards trainCards = (TrainCards) obj;
            view.updateFaceUpCards(trainCards);

        } else if (changeType == ModelChangeType.StartTurn) {
            state = new NoCardsState();
        } else if (changeType == ModelChangeType.BankUpdated) {
            state.bankUpdated(this);
        }

    }

    public interface View {
        void updateFaceUpCards(TrainCards cards);
        void updateDestinationCardCount(int count);
        void updateDrawableTrainCardCount(int count);
    }
}
