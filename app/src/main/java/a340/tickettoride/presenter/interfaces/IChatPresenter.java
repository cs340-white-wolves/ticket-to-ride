package a340.tickettoride.presenter.interfaces;

import java.util.List;

import cs340.TicketToRide.model.game.Message;

public interface IChatPresenter {
    void startObserving();
    void stopObserving();
    List<Message> getChatMessages();
    void onSendPress();
}
