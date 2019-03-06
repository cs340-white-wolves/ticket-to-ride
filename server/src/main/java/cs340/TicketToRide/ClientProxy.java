package cs340.TicketToRide;

import java.util.List;

import cs340.TicketToRide.model.ClientCommandQueue;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;

public class ClientProxy implements IClient {
    public ClientCommandQueue queue = new ClientCommandQueue();

    public ClientCommandQueue getQueue() {
        return queue;
    }

    @Override
    public void chatMessageReceived(ChatMessage message) {

//        Command gotChat = new Command(
//                "chatMessageReceived",
//                new String[]{ChatMessage.class.getName()},
//                new Object[]{message}
//        );

    }

    @Override
    public void playersUpdated(List<Player> players) {

    }
}
