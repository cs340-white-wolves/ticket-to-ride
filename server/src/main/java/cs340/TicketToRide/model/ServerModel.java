package cs340.TicketToRide.model;

import java.util.*;

import cs340.TicketToRide.IClient;
import cs340.TicketToRide.exception.*;
import cs340.TicketToRide.model.game.*;
import cs340.TicketToRide.utility.*;

/**
 * Used to store data in the server side. When the Server performs the operations,
 * it creates, reads, updates, or deletes data stored here in the model.
 */
public class ServerModel implements IServerModel {

    /**
     * Used to Implement the Singleton pattern so only 1 instance is created, accessible anywhere.
     */
    private static ServerModel singleton;

    /**
     * Used to manage auth tokens & associated users (AuthManager contains a map from tokens to users).
     */
    private AuthManager authManager;

    /**
     * Used to manage all the games stored on the server.
     */
    private Games games;

    /**
     * Used to store all the users registered on the server.
     */
    private Set<User> users;

    /**
     * Private constructor (singleton). Initialize empty data.
     */
    private ServerModel() {
        authManager = new AuthManager();
        games = new Games();
        users = new HashSet<>();
    }

    /**
     * Singleton factory method.
     * @return static instance of Server Model
     */
    public static ServerModel getInstance() {
        if (singleton == null) {
            singleton = new ServerModel();
        }
        return singleton;
    }

    /**
     * Returns the user associated with this auth token.
     *
     * @param token A token associated with a user for authorization purposes
     * @return user associated with token
     */
    public User getUserByAuthToken(AuthToken token) {
        if (token == null || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (authManager == null) {
            return null;
        }

        return authManager.getUserByAuthToken(token);
    }

    /**
     * Attempts to add game to server. If the game already exists, the Games class will throw an
     * exception.
     *
     * @param game Game to be added to server
     * @return boolean value signifying if add was successful.
     */
    public boolean addGame(Game game) {
        if (game == null || !game.isValid()) {
            throw new IllegalArgumentException();
        }

        if (games == null) {
            return false;
        }

        return games.addGame(game);
    }

    /**
     * Returns the game object with the given ID.
     *
     * @param gameID Unique ID associated with the game.
     * @return game object associated with the ID.
     */
    public Game getGameByID(ID gameID) {
        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        return games.getGameByID(gameID);
    }

    /**
     * Returns the user with the given username; null if it doesn't exist
     * @param username username associated with desired user.
     * @return User with provided username | null
     */
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

    /**
     * Attempts to add a new User (with username & password) and AuthToken to the server.
     * If the username or auth token already exist for another user, it throws an exception.
     *
     * @param user User to be registered
     * @param token AuthToken to be associated with the User
     * @throws NotUniqueException if The username was already registered
     */
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

    /**
     * Attempts to Login a user by adding an AuthToken to the server.
     * It checks if the User has been created or if the provided token
     * already belongs to another user. If so, it throws an exception. Otherwise
     * @param user User to be logged in
     * @param token Token to be associated with given user.
     * @throws AuthenticationException if the User has not been registered before.
     */
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

    /**
     * Completes basic setup task for a given game (done in the Game class, ie. distribute cards,
     * initialize points, assign orders & colors to players, etc.). Then adds commands to the Client
     * Command Queue for each player in the game so they can be updated and execute those commands on
     * the Client.
     *
     * @param game Game to be initialized
     */
    @Override
    public void setupGame(Game game) {
        ClientProxyManager proxyManager = ClientProxyManager.getInstance();

        game.setup();

        Players players = game.getPlayers();

        // Set updates
        for (Player player : players) {
            IClient clientProxy = proxyManager.get(player.getId());
            clientProxy.playersUpdated(players);
            clientProxy.startGame();
        }
    }

    /**
     * Clears all data on server included registered users, created games, and all auth tokens.
     * Used primarily for testing purposes.
     */
    public void clear() {
        authManager.clear();
        games.clear();
        users.clear();
    }

    /**
     * Simple getter for Games Container which holds all created games.
     * @return Games manager object.
     */
    public Games getGames() {
        return games;
    }

}
