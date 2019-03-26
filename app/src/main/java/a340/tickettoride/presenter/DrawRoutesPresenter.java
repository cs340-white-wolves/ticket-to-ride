package a340.tickettoride.presenter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.Observer;

import a340.tickettoride.R;
import a340.tickettoride.ServiceFacade;
import a340.tickettoride.adapter.DestCardAdapter;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IDrawRoutesPresenter;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;

public class DrawRoutesPresenter implements IDrawRoutesPresenter, ModelObserver {
    private static final int NUM_CARDS_KEEP_TURN = 1;
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
