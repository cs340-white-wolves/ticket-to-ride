package a340.tickettoride.presenter;

import java.util.ArrayList;
import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class BankPresenter implements IBankPresenter, ModelObserver {

    private BankPresenterListener view;

    public BankPresenter(BankPresenterListener listener) {
        ClientModel.getInstance().addObserver(this);
        view = listener;
    }

    @Override
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
        TrainCard card = new TrainCard(null);
        return new TrainCard(TrainCard.Color.tankerBlue);
    }

    @Override
    public TrainCard pickUpTrainCard(int index) {
        return null;
    }


    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        try {
            view.updateFaceUpCards((List<TrainCard>) obj);
        }
        catch(ClassCastException e) {

        }
    }

    public interface BankPresenterListener {
        void updateFaceUpCards(List<TrainCard> cards);
    }
}
