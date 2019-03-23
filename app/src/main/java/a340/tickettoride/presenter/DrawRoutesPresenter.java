package a340.tickettoride.presenter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;

public class DrawRoutesPresenter implements IDrawRoutesPresenter {
    private static final int NUM_CARDS_KEEP_TURN = 1;

    MapPresenter.View view;
    private IClientModel model;

    public DrawRoutesPresenter(MapPresenter.View view) {
        this.view = view;
        this.model = ClientModel.getInstance();
    }

//    @Override
//    public void startObserving() {
//        ClientModel.getInstance().addObserver(this);
//    }
//
//    @Override
//    public void stopObserving() {
//        ClientModel.getInstance().deleteObserver(this);
//    }

//    @Override
//    public void onModelEvent(ModelChangeType changeType, Object obj) {
//        if (changeType == ModelChangeType.GameStarted) {
//            Log.i("MapPresenter", "Game Started!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            view.chooseDestCard();
//
//        } else if (changeType == ModelChangeType.AdvanceTurn) {
//            advanceTurn();
//
//        } else if (changeType == ModelChangeType.RouteClaimed) {
//            view.showRouteIsClaimed((Route) obj);
//        }
//    }

    @Override
    public void chooseDestCards() {
        view.chooseDestCard(NUM_CARDS_KEEP_TURN);
    }

    @Override
    public void discardDestCards() {
        DestinationCards selectedCards = view.getSelectedDestinationCards();
        DestinationCards discardedCards = new DestinationCards();
        Player player = model.getPlayerFromGame();
        DestinationCards allCards = player.getDestinationCards();
        for (DestinationCard card : allCards) {
            if (!selectedCards.contains(card)) {
                discardedCards.add(card);
            }
        }

        ServiceFacade.getInstance().discardDestCards(discardedCards);
    }




}
