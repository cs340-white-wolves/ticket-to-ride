package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import a340.tickettoride.presenter.interfaces.IChatPresenter;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.utility.Username;

public class ChatPresenter implements IChatPresenter {
    private ChatPresenterListener listener;

    public ChatPresenter(ChatPresenterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSendPress() {
        String input = listener.getMessageInput();
        IClientModel model = ClientModel.getInstance();
        Username username = model.getLoggedInUser().getUsername();
        ChatMessage message = new ChatMessage(username, input);
        ServiceFacade.getInstance().sendChatMessage(message);
        listener.clearMessageInput();
    }

    public interface ChatPresenterListener {
        String getMessageInput();
        void clearMessageInput();
    }
}
