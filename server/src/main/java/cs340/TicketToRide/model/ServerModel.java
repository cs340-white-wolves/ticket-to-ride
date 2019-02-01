package cs340.TicketToRide.model;

import cs340.TicketToRide.utility.ID;

public class ServerModel implements IServerModel {
    private static ServerModel singleton;
    private AuthManager authManager;
    private Games games;

    private ServerModel() {
        authManager = new AuthManager();
        games = new Games();
    }

    public static ServerModel getInstance() {
        if (singleton == null) {
            singleton = new ServerModel();
        }
        return singleton;
    }

    public User getUserByAuthToken(AuthToken token) {
        if (token == null || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (authManager == null) {
            return null;
        }

        return authManager.getUserByAuthToken(token);
    }

    public void addGame(Game game) {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        games.addGame(game);
    }

    public Game getGameByID(ID gameID) {
        return games.getGameByID(gameID);
    }
}
