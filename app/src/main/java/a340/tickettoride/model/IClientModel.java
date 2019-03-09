package a340.tickettoride.model;

import java.util.List;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.utility.ID;

public interface IClientModel {
    void onAuthenticateFail(Exception e);
    void onAuthenticateSuccess(LoginRegisterResponse token);
    AuthToken getAuthToken();
    void onJoinGameSuccess(Game game);
    void onJoinGameFail(Exception e);
    void onCreateGameSuccess(Game game);
    void onCreateGameFail(Exception e);
    void onChatMessageReceived(ChatMessage message);
    int getLastExecutedCommandIndex();
    Game getActiveGame();
    ID getPlayerId();
    Player getPlayerFromGame();
    void onDiscardDestCardsFail(Exception exception);
    void updateGameDestCardDeck(DestinationCards destCardDeck);
    void updatePlayers(Players players);
    void onSendChatFail(Exception exception);
    User getLoggedInUser();
    void onGameStart();
    boolean activePlayerTurn();
    List<ChatMessage> getChatMessages();
    void startGameCommandPoller();
    void updateFaceUpTrainCards(List<TrainCard> faceUpCards);
}
