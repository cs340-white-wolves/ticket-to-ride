package a340.tickettoride.model;

import java.util.Observable;

import a340.tickettoride.communication.Poller;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.User;

public class ClientModel extends Observable implements IClientModel, Poller.Listener {
    private static ClientModel singleton;

    private User loggedInUser;
    private AuthToken authToken;
    private Game activeGame;
    private Games lobbyGameList;

    private ClientModel() {
    }

    public static ClientModel getInstance() {
        if (singleton == null) {
            singleton = new ClientModel();
        }

        return singleton;
    }

    public void onPollComplete(Games lobbyGameList) {
        setLobbyGameList(lobbyGameList);
        notifyObservers(lobbyGameList);
    }

    public void onAuthenticateFail(Exception e) {
        setChanged();
        notifyObservers(e);
    }

    public void onAuthenticateSuccess(LoginRegisterResponse response) {
        setAuthToken(response.getToken());
        setLoggedInUser(response.getUser());
// todo:        startPoller();
        setChanged();
        notifyObservers(response);
    }

    public void onJoinGameSuccess(Game game) {
        setActiveGame(game);
        notifyObservers(game);
    }

    public void onJoinGameFail(Exception e) {
        notifyObservers(e);
    }

    @Override
    public void onCreateGameSuccess(Game game) {
        setActiveGame(game);
        notifyObservers(game);
    }

    @Override
    public void onCreateGameFail(Exception e) {
        notifyObservers(e);
    }

    private void startPoller() {
        new Poller(this).run();
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setActiveGame(Game activeGame) {
        this.activeGame = activeGame;
    }

    public void setLobbyGameList(Games lobbyGameList) {
        this.lobbyGameList = lobbyGameList;
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
