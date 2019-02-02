package cs340.TicketToRide;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs340.TicketToRide.communication.ServerCommand;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

import static org.junit.jupiter.api.Assertions.*;

public class ServerCommandTest {

    @Test
    void execute() {
        Class<?>[] paramTypes = {Username.class, Password.class};
        Object[] params = {new Username("nate"), new Password("1234")};
        ServerCommand command = new ServerCommand("login", paramTypes, params);
        Object result = command.execute(ServerFacade.getInstance());
        if (result instanceof Throwable) {
            System.out.println(((Throwable)result).getMessage());
            return;
        }

        AuthToken token = (AuthToken)result;
        System.out.println(token.toString());
    }
}
