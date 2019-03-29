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
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCards;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public class ClientModel extends ModelObservable implements IClientModel, Poller.Listener {
    private Poller poller = new Poller(this);
    private static ClientModel singleton;
    private User loggedInUser;
    private AuthToken authToken;
    private Game activeGame;
    private Games lobbyGameList;
    private ID playerId;
    private List<Message> chatMessages = new ArrayList<>();
    private List<Message> historyMessages = new ArrayList<>();
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
        notifyObservers(ModelChangeType.AvailableGameList, lobbyGameList);
    }

    @Override
    public void onPollComplete(Commands queuedCommands) {
        int startIndex = queuedCommands.getStartIndex();

        for (Command cmd : queuedCommands.getAll()) {
            // make sure we have not executed the command before
            if (startIndex >= lastExecutedCommandIndex) {
                cmd.execute(ClientFacade.getInstance());
            } else {
                Log.i("ClientModel", "Skipping commands for some reason!!");
            }
            startIndex++;
        }

        // Check if this causes bugs
        if (lastExecutedCommandIndex < queuedCommands.getEndIndex()) {
            lastExecutedCommandIndex = queuedCommands.getEndIndex();
        }
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
            notifyObservers(ModelChangeType.StartMap, null);
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
        Players players = game.getPlayers();

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
    public void onChatMessageReceived(Message message) {
        chatMessages.add(message);
        notifyObservers(ModelChangeType.ChatMessageReceived, chatMessages);
    }

    @Override
    public void onHistoryMessageReceived(Message message) {
        historyMessages.add(message);
        notifyObservers(ModelChangeType.GameHistoryReceived, historyMessages);
    }

    private void startGameListPoller() {
        poller.runUpdateGameList();
    }

    public void startGameCommandPoller() {
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
        Log.i("ClientModel", "->startGame()");
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
    public void updatePlayers(Players players) {
        Log.i("ClientModel", "->updatePlayers()");
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

    public List<Message> getChatMessages() {
        return chatMessages;
    }


    @Override
    public void updateGameDestCardDeck(DestinationCards destCardDeck) {
        activeGame.setDestinationCardDeck(destCardDeck);
        notifyObservers(ModelChangeType.DrawableDestinationCardCount, activeGame.getDestCardDeck().size());
    }

    @Override
    public void updateTrainCardDeck(TrainCards cards) {
        activeGame.setTrainCardDeck(cards);
        notifyObservers(ModelChangeType.DrawableTrainCardCount, activeGame.getTrainCardDeck().size());

    }

    @Override
    public void updateFaceUpTrainCards(TrainCards faceUpCards) {
        activeGame.setFaceUpTrainCards(faceUpCards);
        notifyObservers(ModelChangeType.FaceUpTrainCardsUpdate, activeGame.getFaceUpTrainCards());
        notifyObservers(ModelChangeType.DrawableTrainCardCount, activeGame.getTrainCardDeck().size());
    }

    public void updateRoute(Route route) {
        notifyObservers(ModelChangeType.RouteClaimed, route);
    }

    @Override
    public void onDestCardsAdded(DestinationCards cardsToAddToPlayer) {
        notifyObservers(ModelChangeType.DestCardsAdded, cardsToAddToPlayer);
    }

    @Override
    public List<Message> getHistoryMessages() {
        return historyMessages;
    }

    public void setHistoryMessages(List<Message> historyMessages) {
        this.historyMessages = historyMessages;
    }

    public void startTurn() {
        notifyObservers(ModelChangeType.StartTurn, null);
    }

    public Players getPlayers() {
        return this.activeGame.getPlayers();
    }


    public void setTurn(int playerIdx) {
        activeGame.setCurrentPlayerTurnIdx(playerIdx);
        notifyObservers(ModelChangeType.SetTurn, playerIdx);
    }

    @Override
    public void setLastRoundLastPlayerId(ID playerId) {
        activeGame.setLastRoundLastPlayerId(playerId);
    }

    @Override
    public void endGame() {
        notifyObservers(ModelChangeType.EndGame, activeGame.getPlayers());
    }



    //=================================Testing Methods===============================


    public void addChatMessages() {
        for(Player player: activeGame.getPlayers()) {
            Username username = player.getUser().getUsername();
            chatMessages.add(new Message(username, "Message from " + username.toString()));
        }
        notifyObservers(ModelChangeType.ChatMessageReceived, chatMessages);
    }

    public void updateActivePlayersPoints(int points) {
        Player player = getPlayerFromGame();
        player.setScore(points);
        updatePlayers(activeGame.getPlayers());
    }

    public void updatePlayersTrainCards(TrainCards cards) {
        Player player = getPlayerFromGame();
        player.getTrainCards().addAll(cards);
        updatePlayers(activeGame.getPlayers());
    }

    public void removePlayersTrainCards() {
        Player player = getPlayerFromGame();
        player.getTrainCards().clear();
        updatePlayers(activeGame.getPlayers());
    }

    public void updatePlayersDestCards(DestinationCards cards) {
        Player player = getPlayerFromGame();
        player.getDestinationCards().addAll(cards);
        updatePlayers(activeGame.getPlayers());
    }

    public void removePlayersDestCards() {
        Player player = getPlayerFromGame();
        player.getDestinationCards().clear();
        updatePlayers(activeGame.getPlayers());
    }

    public void updateOpponentsTrainCards(TrainCards cards) {
        Players players = activeGame.getPlayers();

        for(Player player: players) {
            if (!player.getId().equals(playerId)) {
                player.setTrainCards(cards);
            }
        }
        updatePlayers(activeGame.getPlayers());
    }

    public void updateOpponentsDestCards(DestinationCards cards) {
        Players players = activeGame.getPlayers();

        for(Player player: players) {
            if (!player.getId().equals(playerId)) {
                player.getDestinationCards().addAll(cards);
            }
        }
        updatePlayers(activeGame.getPlayers());
    }

    public void updateOpponentsTrainCars(int number) {
        Players players = activeGame.getPlayers();

        for(Player player: players) {
            if (!player.getId().equals(playerId)) {
                player.setNumTrains(number);
            }
        }

        updatePlayers(activeGame.getPlayers());
    }


    public void onSelectedSingleCard() {
        notifyObservers(ModelChangeType.SelectedSingleCard, null);
    }
}
