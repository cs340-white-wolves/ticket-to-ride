package cs340.TicketToRide.service;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.NotUniqueException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class RegisterService {
    public LoginRegisterResponse register(Username username, Password password) throws NotUniqueException {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByUsername(username);

        if (user != null) {
            throw new NotUniqueException("This username has already been registered");
        }

        user = new User(username, password);
        AuthToken token = AuthToken.generateToken();
        model.registerUser(user, token);
        return new LoginRegisterResponse(token, user);
    }
}
