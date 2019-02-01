package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class RegisterService {
    public AuthToken register(Username username, Password password) {
        if (username == null || password == null || !username.isValid() || !password.isValid()) {
            throw new IllegalArgumentException();
        }

        IServerModel model = ServerModel.getInstance();
        User user = model.getUserByUsername(username);
        if (user != null) {
            return null;
            // todo: exception already exists
        }

        user = new User(username, password);
        AuthToken token = AuthToken.generateToken();
        model.registerUser(user, token);
        return token;
    }
}
