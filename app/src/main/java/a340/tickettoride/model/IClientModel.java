package a340.tickettoride.model;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.utility.ID;

public interface IClientModel {
    void onAuthenticateFail(Exception e);
    void onAuthenticateSuccess(LoginRegisterResponse token);
    AuthToken getAuthToken();
    void onJoinGameSuccess(Game game);
    void onJoinGameFail(Exception e);
    void onCreateGameSuccess(Game game);
    void onCreateGameFail(Exception e);
    ID getPlayerId();
}
