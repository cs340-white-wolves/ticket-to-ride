package cs340.TicketToRide.communication;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.User;

public class LoginRegisterResponse {
    private AuthToken token;
    private User user;

    public LoginRegisterResponse(AuthToken token, User user) {
        setToken(token);
        setUser(user);
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
