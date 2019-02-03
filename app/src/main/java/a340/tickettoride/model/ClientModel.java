package a340.tickettoride.model;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.User;

public class ClientModel {
    private static ClientModel singleton;

    private User loggedInUser;
    private AuthToken authToken;
    private Game activeGame;
    private Games lobbyGameList;

    private ClientModel() {
    }

    public ClientModel getInstance() {
        if (singleton == null) {
            singleton = new ClientModel();
        }

        return singleton;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public Game getActiveGame() {
        return activeGame;
    }

    public Games getLobbyGameList() {
        return lobbyGameList;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
