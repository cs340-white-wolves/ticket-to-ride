package a340.tickettoride.model;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import a340.tickettoride.communication.Poller;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObservable;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.User;

public class ClientModel extends ModelObservable implements IClientModel, Poller.Listener {
    private Poller poller = new Poller(this);
    private static ClientModel singleton;

    private User loggedInUser;
    private AuthToken authToken;
    private Game activeGame;
    private Games lobbyGameList;

    private ClientModel() {
        Log.i("ClientModel", "I'm alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        Log.i("ClientModel", "I'm DEAD!");

        super.finalize();
    }

    public static ClientModel getInstance() {
        if (singleton == null) {
            singleton = new ClientModel();
        }

        return singleton;
    }

    public void onPollComplete(Games gameList) {
        setLobbyGameList(gameList);
        setActiveGameFromGames(gameList);
        notifyObservers(ModelChangeType.AvailableGameList, gameList);
    }

    private void setActiveGameFromGames(Games lobbyGameList) {
        if (activeGame == null || lobbyGameList == null || lobbyGameList.size() == 0) {
            return;
        }

        Game game = lobbyGameList.getGameByID(activeGame.getGameID());

        if (game == null) {
            return;
        }

        // The players in the active game change!
        setActiveGame(game);
    }

    public void onAuthenticateFail(Exception e) {
        notifyObservers(ModelChangeType.FailureException, e);
    }

    public void onAuthenticateSuccess(LoginRegisterResponse response) {
        setAuthToken(response.getToken());
        setLoggedInUser(response.getUser());
        startPoller();
        notifyObservers(ModelChangeType.AuthenticateSuccess, response);
    }

    public void onJoinGameSuccess(Game game) {
        setActiveGame(game);
        notifyObservers(ModelChangeType.JoinGame, game);
    }

    public void onJoinGameFail(Exception e) {
        notifyObservers(ModelChangeType.FailureException, e);
    }

    @Override
    public void onCreateGameSuccess(Game game) {
        setActiveGame(game);
        notifyObservers(ModelChangeType.JoinGame, game);
    }

    @Override
    public void onCreateGameFail(Exception e) {
        notifyObservers(ModelChangeType.FailureException, e);
    }

    private void startPoller() {
        poller = new Poller(this);
        poller.run();
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
        Set<Game> gameSet = lobbyGameList.getGameSet();

        Games lobbyGames = new Games();

        for (Game game : gameSet) {
            if (!game.hasTargetNumPlayers()) {
                lobbyGames.addGame(game);
            }
        }

        this.lobbyGameList = lobbyGames;
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
