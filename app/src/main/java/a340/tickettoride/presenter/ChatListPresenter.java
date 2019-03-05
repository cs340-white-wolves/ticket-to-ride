package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import cs340.TicketToRide.model.game.ChatMessage;

public class ChatListPresenter implements IChatListPresenter, ModelObserver {
    private ChatListPresenterListener listener;

    public ChatListPresenter(ChatListPresenterListener listener) {
        this.listener = listener;
    }

    @Override
    public void startObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void stopObserving() {
        ClientModel.getInstance().deleteObserver(this);
    }

    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.ChatMessageReceived) {
            List<ChatMessage> msgs = (List<ChatMessage>) obj;
            listener.updateChatMessages(msgs);
        }
    }

    public interface ChatListPresenterListener {
        void updateChatMessages(List<ChatMessage> messages);
    }
}
