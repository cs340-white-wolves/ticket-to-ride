package a340.tickettoride.model;

import cs340.TicketToRide.communication.LoginRegisterResponse;

public interface IClientModel {
    void onAuthenticateFail(Exception e);

    void onAuthenticateSuccess(LoginRegisterResponse token);

}
