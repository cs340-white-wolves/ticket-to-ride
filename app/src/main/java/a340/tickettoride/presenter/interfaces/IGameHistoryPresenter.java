package a340.tickettoride.presenter.interfaces;

import java.util.List;

import cs340.TicketToRide.model.game.Message;

public interface IGameHistoryPresenter {
    void startObserving();
    void stopObserving();
    List<Message> getHistoryMessages();
}
