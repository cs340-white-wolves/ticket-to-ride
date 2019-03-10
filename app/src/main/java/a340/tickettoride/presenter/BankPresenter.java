package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public class BankPresenter implements IBankPresenter, ModelObserver {

    private View view;
    private IClientModel model = ClientModel.getInstance();

    public BankPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public TrainCards getCurrentFaceUpCards() {

        return model.getActiveGame().getFaceUpTrainCards();
    }

    @Override
    public TrainCard drawTrainCard() {

        return new TrainCard(TrainCard.Color.tankerBlue);
    }

    @Override
    public TrainCard pickUpTrainCard(int index) {
        return null;
    }

    @Override
    public int getNumTrainCards() { return model.getActiveGame().getTrainCardDeck().size(); }

    @Override
    public int getNumDestCards() {
        return model.getActiveGame().getDestCardDeck().size();
    }

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
        }

    }

    public interface View {
        void updateFaceUpCards(TrainCards cards);
        void updateDestinationCardCount(int count);
        void updateDrawableTrainCardCount(int count);
    }
}
