package cs340.TicketToRide.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs340.TicketToRide.ClientProxy;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.NotUniqueException;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
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

    public boolean addGame(Game game) {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        if (games == null) {
            return false;
        }

        return games.addGame(game);
    }

    public Game getGameByID(ID gameID) {
        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

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

    public void registerUser(User user, AuthToken token) throws NotUniqueException {
        if (user == null || token == null || !user.isValid() || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (users.contains(user)) {
            throw new NotUniqueException("This username has already been registered");
        }

        if (authManager.getUserByAuthToken(token) != null) {
            throw new RuntimeException("This auth token already belongs to a user");
        }

        users.add(user);
        authManager.addTokenUser(token, user);
    }

    public void loginUser(User user, AuthToken token) throws AuthenticationException {
        if (user == null || token == null || !user.isValid() || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (!users.contains(user)) {
            throw new AuthenticationException("This username has not been registered");
        }

        if (authManager.getUserByAuthToken(token) != null) {
            throw new RuntimeException("This auth token already belongs to a user");
        }

        authManager.addTokenUser(token, user);
    }

    @Override
    public void setupGame(Game game) {
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();

        game.setup();

        List<Player> players = game.getPlayers();

        // Set updates
        for (Player player : players) {
            IClient clientProxy = proxyManager.get(player.getId());
            clientProxy.playersUpdated(players);
        }
    }

    @Override
    public Player getPlayerById(ID playerId) {
        return null;
    }

    public void clear() {
        authManager.clear();
        games.clear();
        users.clear();
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public void setAuthManager(AuthManager authManager) {
        this.authManager = authManager;
    }

    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
