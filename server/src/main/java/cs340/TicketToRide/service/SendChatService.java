package cs340.TicketToRide.service;

import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.utility.ID;

public class SendChatService {
    public void sendChat(AuthToken token, ID gameId, Message message) {
        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByAuthToken(token);
        if (user == null) {
            throw new AuthenticationException("Invalid Auth token");
        }

        if (!message.getUsername().equals(user.getUsername())) {
            throw new RuntimeException("Chat message username doesn't match");
        }

        Game game = model.getGameByID(gameId);

        if (game == null) {
            throw new RuntimeException("Game does not exist");
        }

        Players players = game.getPlayers();

        for (Player player : players) {
            ClientProxyManager.getInstance()
                    .get(player.getId())
                    .chatMessageReceived(message);
        }
    }
}
