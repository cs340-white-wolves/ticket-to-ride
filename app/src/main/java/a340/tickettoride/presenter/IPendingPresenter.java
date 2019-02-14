package a340.tickettoride.presenter;

import cs340.TicketToRide.model.game.Game;

public interface IPendingPresenter {
    Game getActiveGame();
    void startObserving();
    void stopObserving();
}
