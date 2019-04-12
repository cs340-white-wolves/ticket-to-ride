package cs340.TicketToRide;

import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.*;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class CommandTest {

    private ServerModel serverModel = ServerModel.getInstance();
    private ServerFacade serverFacade = ServerFacade.getInstance();

    @Test
    public void executeRegisterLocal() {
        serverModel.clear();

        String[] paramTypes = {Username.class.getName(), Password.class.getName()};
        Object[] params = {new Username("nate"), new Password("1234")};
        Command command = new Command("register", paramTypes, params);
        Object result = command.execute(serverFacade);
        assertNotNull(result);
    }

    @Test
    public void executeRegisterRemote() {
        serverModel.clear();

        String[] paramTypes = {Username.class.getName(), Password.class.getName()};
        Object[] params = {new Username("nate"), new Password("1234")};
        Command command = new Command("register", paramTypes, params);

        // Pretend to send over network (serialize and deserialize)
        Gson gson = new Gson();
        String json = gson.toJson(command);
        Command cmd2 = gson.fromJson(json, Command.class);

        assertEquals(command, cmd2);

        Object result = cmd2.execute(ServerFacade.getInstance());

        assertNotNull(result);
    }
}
