package a340.tickettoride.presenter;

import java.util.Set;

import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Player;

public interface IPendingPresenter {
    Game getActiveGame();
    void startObserving();
    void stopObserving();
}
