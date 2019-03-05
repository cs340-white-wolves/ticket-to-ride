package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.presenter.interfaces.IChatPresenter;

public class ChatPresenter implements IChatPresenter {
    private ChatPresenterListener listener;

    public ChatPresenter(ChatPresenterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSendPress() {
        String input = listener.getMessageInput();
        ServiceFacade.getInstance().sendChatMessage(input);
        listener.clearMessageInput();
    }

    public interface ChatPresenterListener {
        String getMessageInput();
        void clearMessageInput();
    }
}
