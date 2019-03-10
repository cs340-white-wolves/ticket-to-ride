package a340.tickettoride.presenter.interfaces;

import cs340.TicketToRide.model.game.card.DestinationCards;

public interface IRoutesPresenter {
    DestinationCards getPlayerDestCards();
    void startObserving();
    void stopObserving();


}
