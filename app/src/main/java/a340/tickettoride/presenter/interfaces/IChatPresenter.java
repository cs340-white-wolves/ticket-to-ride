package a340.tickettoride.presenter.interfaces;

import java.util.List;

import cs340.TicketToRide.model.game.ChatMessage;

public interface IChatPresenter {
    void startObserving();
    void stopObserving();
    List<ChatMessage> getChatMessages();
    void onSendPress();
}
