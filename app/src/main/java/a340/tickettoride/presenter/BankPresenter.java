package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public class BankPresenter implements IBankPresenter, ModelObserver {

    private View view;

    public BankPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public TrainCards getCurrentFaceUpCards() {

        TrainCards list = new TrainCards();

        list.add(new TrainCard(TrainCard.Color.cabooseGreen));
        list.add(new TrainCard(TrainCard.Color.freightOrange));
        list.add(new TrainCard(TrainCard.Color.hopperBlack));
        list.add(new TrainCard(TrainCard.Color.passengerWhite));
        list.add(new TrainCard(TrainCard.Color.locomotive));

        return list;
    }

    @Override
    public TrainCard drawTrainCard() {
        TrainCard card = new TrainCard(null);
        return new TrainCard(TrainCard.Color.tankerBlue);
    }

    @Override
    public TrainCard pickUpTrainCard(int index) {
        return null;
    }


    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.DrawableDestinationCardCount) {
            int count = (int) obj;
            view.updateDestinationCardCount(count);
        } else if (changeType == ModelChangeType.FaceUpTrainCardsUpdate) {
            TrainCards trainCards = (TrainCards) obj;
            view.updateFaceUpCards(trainCards);
        }
    }

    public interface View {
        void updateFaceUpCards(TrainCards cards);
        void updateDestinationCardCount(int count);
    }
}
