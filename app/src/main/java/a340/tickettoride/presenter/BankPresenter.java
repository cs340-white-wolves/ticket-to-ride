package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class BankPresenter implements IBankPresenter, ModelObserver {

    private View view;

    public BankPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }


    public List<TrainCard> getCurrentFaceUpCards() {

        List<TrainCard> list = new ArrayList<>(5);

        list.add(new TrainCard(TrainCard.Color.cabooseGreen));
        list.add(new TrainCard(TrainCard.Color.freightOrange));
        list.add(new TrainCard(TrainCard.Color.hopperBlack));
        list.add(new TrainCard(TrainCard.Color.passengerWhite));
        list.add(new TrainCard(TrainCard.Color.locomotive));

        return list;
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
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.DrawableDestinationCardCount) {
            int count = (int) obj;
            view.updateDestinationCardCount(count);

        } else if (changeType == ModelChangeType.DrawableTrainCardCount) {
            int count = (int) obj;
            view.updateDrawableTrainCardCount(count);

        } else if (changeType == ModelChangeType.FaceUpTrainCardsUpdate) {
            List<TrainCard> trainCards = (List<TrainCard>)obj;
            view.updateFaceUpCards(trainCards);
        }

    }

    public interface View {
        void updateFaceUpCards(List<TrainCard> cards);
        void updateDestinationCardCount(int count);
        void updateDrawableTrainCardCount(int count);
    }
}
