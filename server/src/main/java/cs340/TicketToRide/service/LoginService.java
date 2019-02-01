package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class LoginService {
    public AuthToken login(Username username, Password password) {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        return null;
    }
}
