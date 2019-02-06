package a340.tickettoride.model;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;

public interface IClientModel {
    void onAuthenticateFail(Exception e);
    void onAuthenticateSuccess(LoginRegisterResponse token);
    AuthToken getAuthToken();
    void onJoinGameSuccess();
    void onJoinGameFail(Exception e);
}
