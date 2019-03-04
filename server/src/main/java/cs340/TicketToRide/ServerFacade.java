package cs340.TicketToRide;

import java.util.Set;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.service.CreateGameService;
import cs340.TicketToRide.service.QueueService;
import cs340.TicketToRide.service.GamesService;
import cs340.TicketToRide.service.JoinGameService;
import cs340.TicketToRide.service.LoginService;
import cs340.TicketToRide.service.RegisterService;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class ServerFacade implements IServer {

    private static ServerFacade singleton;

    private ServerFacade() {
    }

    public static ServerFacade getInstance() {
        if (singleton == null) {
            singleton = new ServerFacade();
        }
        return singleton;
    }

    public LoginRegisterResponse login(Username username, Password password) {
        return new LoginService().login(username, password);
    }

    public LoginRegisterResponse register(Username username, Password password) {
        return new RegisterService().register(username, password);
    }

    public Game createGame(AuthToken token, int numPlayers) {
        return new CreateGameService().createGame(token, numPlayers);
    }

    public Game joinGame(AuthToken token, ID gameId) {
        return new JoinGameService().joinGame(token, gameId);
    }

    public Commands getQueuedCommands(AuthToken token, Player p, int index) {
        return new QueueService().getQueuedCommands(token, p, index);
    }

    public Games getAvailableGames(AuthToken token) {
        return new GamesService().getAvailableGames(token);
    }

    @Override
    public void sendChat(AuthToken token, ID gameId, ChatMessage message) {
        IServerModel model = ServerModel.getInstance();

        Game game = model.getGameByID(gameId);

        Set<Player> players = game.getPlayers();

        for (Player player : players) {
            Command gotChat = new Command(
                    "gotChat",
                    new String[]{ChatMessage.class.getName()},
                    new Object[]{message}
            );
            model.getClientQueueManager().put(player, gotChat);
        }

    }

}
