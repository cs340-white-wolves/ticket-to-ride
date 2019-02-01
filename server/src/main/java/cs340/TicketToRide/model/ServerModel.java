package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public class ServerModel implements IServerModel {
    private static ServerModel singleton;
    private AuthManager authManager;
    private Games games;
    private Set<User> users;

    private ServerModel() {
        authManager = new AuthManager();
        games = new Games();
        users = new HashSet<>();
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

    public User getUserByUsername(Username username) {
        if (username == null || !username.isValid()) {
            throw new IllegalArgumentException();
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public void registerUser(User user, AuthToken token) {
        if (user == null || token == null || !user.isValid() || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (users.contains(user)) {
            // todo: throw exception?
            return;
        }

        if (authManager.getUserByAuthToken(token) != null) {
            // todo: throw exception?
            return;
        }

        users.add(user);
        authManager.addTokenUser(token, user);
    }

    public void loginUser(User user, AuthToken token) {
        if (user == null || token == null || !user.isValid() || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (!users.contains(user)) {
            // todo: throw exception?
            return;
        }

        if (authManager.getUserByAuthToken(token) != null) {
            // todo: throw exception?
            return;
        }

        authManager.addTokenUser(token, user);
    }
}
