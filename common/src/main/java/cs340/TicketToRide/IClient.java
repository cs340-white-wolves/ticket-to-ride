package cs340.TicketToRide;

import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.utility.ID;

public interface IClient {
    void gotChat(ChatMessage message);
}
