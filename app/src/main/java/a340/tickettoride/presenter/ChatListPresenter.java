package a340.tickettoride.presenter;

import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IChatListPresenter;
import cs340.TicketToRide.model.game.ChatMessage;

public class ChatListPresenter implements IChatListPresenter, ModelObserver {
    private View listener;
    private IClientModel model = ClientModel.getInstance();

    public ChatListPresenter(View listener) {
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
            List<ChatMessage> messages = (List<ChatMessage>) obj;
            listener.setChatMsgsFromPoller(messages);
        }
    }

    public List<ChatMessage> getChatMessages() {
        return model.getChatMessages();
    }

    public interface View {
        void setChatMsgsFromPoller(List<ChatMessage> message);
    }
}
