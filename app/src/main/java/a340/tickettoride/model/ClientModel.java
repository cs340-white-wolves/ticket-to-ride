package a340.tickettoride.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import a340.tickettoride.ClientFacade;
import a340.tickettoride.communication.Poller;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObservable;
import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.utility.ID;

public class ClientModel extends ModelObservable implements IClientModel, Poller.Listener {
    private Poller poller = new Poller(this);
    private static ClientModel singleton;
    private User loggedInUser;
    private AuthToken authToken;
    private Game activeGame;
    private Games lobbyGameList;
    private ID playerId;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private int lastExecutedCommandIndex = -1;

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

    @Override
    public void onPollComplete(Commands queuedCommands) {
        int startIndex = queuedCommands.getStartIndex();

        for (Command cmd : queuedCommands.getAll()) {
            // make sure we have not executed the command before
            if (startIndex >= lastExecutedCommandIndex) {
                cmd.execute(ClientFacade.getInstance());
            }
            else {
                Log.i("ClientModel", "Skipping commands for some reason!!");
            }
            startIndex++;
        }

        lastExecutedCommandIndex = queuedCommands.getEndIndex();
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

        if (game.hasTargetNumPlayers()) {
            stopPoller();
            startGameCommandPoller();
        }
    }

    public void onAuthenticateFail(Exception e) {
        notifyObservers(ModelChangeType.FailureException, e);
    }

    public void onAuthenticateSuccess(LoginRegisterResponse response) {
        setAuthToken(response.getToken());
        setLoggedInUser(response.getUser());
        startGameListPoller();
        notifyObservers(ModelChangeType.AuthenticateSuccess, response);
    }

    private void setActivePlayerId(Game game) {
        List<Player> players = game.getPlayers();

        for (Player player : players) {
            if (player.getUser().equals(getLoggedInUser())) {
                setPlayerId(player.getId());
            }
        }
    }

    public void onJoinGameSuccess(Game game) {
        setActivePlayerId(game);
        setActiveGame(game);
        notifyObservers(ModelChangeType.JoinGame, game);
    }

    public void onJoinGameFail(Exception e) {
        notifyObservers(ModelChangeType.FailureException, e);
    }

    @Override
    public void onCreateGameSuccess(Game game) {
        setActivePlayerId(game);
        setActiveGame(game);
        notifyObservers(ModelChangeType.JoinGame, game);
    }

    @Override
    public void onCreateGameFail(Exception e) {
        notifyObservers(ModelChangeType.FailureException, e);
    }

    @Override
    public void onChatMessageReceived(ChatMessage message) {
        chatMessages.add(message);
        notifyObservers(ModelChangeType.ChatMessageReceived, chatMessages);
    }

    private void startGameListPoller() {
        poller.runUpdateGameList();
    }

    private void startGameCommandPoller () {
        poller.runGetGameCommands();
    }

    private void stopPoller() {
        poller.stop();
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

    @Override
    public void onGameStart() {
        notifyObservers(ModelChangeType.GameStarted, null);
    }

    @Override
    public boolean activePlayerTurn() {
        return activeGame.isPlayerTurn(playerId);
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

    public ID getPlayerId() {
        return playerId;
    }

    @Override
    public Player getPlayerFromGame() {
        for (Player player : activeGame.getPlayers()) {
            if (player.getId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public void onDiscardDestCardsFail(Exception exception) {

    }

    @Override
    public void updateGameDestCardDeck(Deck<DestinationCard> destCardDeck) {
        activeGame.setDestinationCardDeck(destCardDeck);
        notifyObservers(ModelChangeType.DrawableDestinationCardCount, destCardDeck.size());
    }

    @Override
    public void updatePlayers(List<Player> players) {
        activeGame.setPlayers(players);
        notifyObservers(ModelChangeType.UpdatePlayers, players);
        notifyObservers(ModelChangeType.UpdatePlayerHand, activeGame.getPlayerById(playerId));
    }

    @Override
    public void onSendChatFail(Exception exception) {

    }

    public void setPlayerId(ID playerId) {
        this.playerId = playerId;
    }

    public int getLastExecutedCommandIndex() {
        return lastExecutedCommandIndex;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }
}
