package a340.tickettoride.presenter.interfaces;

import java.util.List;

import cs340.TicketToRide.model.game.ChatMessage;

public interface IChatListPresenter {
    void startObserving();
    void stopObserving();
    public List<ChatMessage> getChatMessages();
}
