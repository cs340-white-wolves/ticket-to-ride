package cs340.TicketToRide.service;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class LoginService {
    public LoginRegisterResponse login(Username username, Password password) throws AuthenticationException {

        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid Username/Password Combination");
        }

        AuthToken token = AuthToken.generateToken();
        model.loginUser(user, token);
        return new LoginRegisterResponse(token, user);
    }
}
