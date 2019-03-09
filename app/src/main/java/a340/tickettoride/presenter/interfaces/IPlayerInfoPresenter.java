package a340.tickettoride.presenter.interfaces;

import cs340.TicketToRide.model.game.Players;

public interface IPlayerInfoPresenter {
    void startObserving();
    void stopObserving();
    Players getPlayers();
}
