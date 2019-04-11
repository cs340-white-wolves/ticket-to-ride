package a340.tickettoride.presenter;


import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IDrawRoutesPresenter;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;

public class DrawRoutesPresenter implements IDrawRoutesPresenter, ModelObserver {
    public static final int INITIAL_MIN_DEST_CARDS = 2;
    public static final int STANDARD_MIN_DEST_CARDS = 1;

    MapPresenter.View view;
    private IClientModel model;

    public DrawRoutesPresenter(MapPresenter.View view) {
        this.view = view;
        this.model = ClientModel.getInstance();
    }

    @Override
    public void startObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void stopObserving() {
        ClientModel.getInstance().deleteObserver(this);
    }

    private DestinationCards getPlayerDestCards() {
        return model.getPlayerFromGame().getDestinationCards();
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.GameStarted) {
            view.chooseDestCard(getPlayerDestCards(), INITIAL_MIN_DEST_CARDS);

        } else if (changeType == ModelChangeType.DestCardsAdded) {
            view.chooseDestCard((DestinationCards)obj, STANDARD_MIN_DEST_CARDS);
        }
    }


    @Override
    public void discardDestCards() {
        DestinationCards selectedCards = view.getSelectedDestinationCards();
        DestinationCards discardedCards = new DestinationCards();
        DestinationCards recentlyAdded = view.getRecentlyAddedDestCards();
        for (DestinationCard card : recentlyAdded) {
            if (!selectedCards.contains(card)) {
                discardedCards.add(card);
            }
        }

        ServiceFacade.getInstance().discardDestCards(discardedCards);
    }




}
