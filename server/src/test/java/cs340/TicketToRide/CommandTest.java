package cs340.TicketToRide;

import org.junit.jupiter.api.Test;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class CommandTest {

    @Test
    void execute() {
        String[] paramTypes = {Username.class.getName(), Password.class.getName()};
        Object[] params = {new Username("nate"), new Password("1234")};
        Command command = new Command("login", paramTypes, params);
        Object result = command.execute(ServerFacade.getInstance());
        if (result instanceof Throwable) {
            System.out.println(((Throwable)result).getMessage());
            return;
        }
        AuthToken token = (AuthToken)result;
        System.out.println(token.toString());
    }
}
