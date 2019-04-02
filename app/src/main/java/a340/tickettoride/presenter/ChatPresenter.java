package a340.tickettoride.presenter;

import java.nio.charset.Charset;
import java.util.List;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IChatPresenter;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.utility.Username;


public class ChatPresenter implements IChatPresenter, ModelObserver {
    private View listener;

    private IClientModel model = ClientModel.getInstance();

    public ChatPresenter(View listener) {
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
            List<Message> messages = (List<Message>) obj;
            listener.setChatMsgsFromPoller(messages);
        }
    }

    public List<Message> getChatMessages() {
        return model.getChatMessages();
    }

    @Override
    public void onSendPress() {
        // in this case, they case send input like "    " but I think it's fine
        String input = listener.getMessageInput();
        if (input == null || input.equals("")) {
            return;
        }

        if (!Charset.forName("US-ASCII").newEncoder().canEncode(input)) {
            listener.clearMessageInput();
            listener.displayMessage("Invalid Message");
            return;
        }

        IClientModel model = ClientModel.getInstance();
        Username username = model.getLoggedInUser().getUsername();
        Message message = new Message(username, input);
        ServiceFacade.getInstance().sendChatMessage(message);
        listener.clearMessageInput();
    }

    public interface View {
        void setChatMsgsFromPoller(List<Message> message);
        String getMessageInput();
        void clearMessageInput();
        void displayMessage(String invalid_message);
    }

}
