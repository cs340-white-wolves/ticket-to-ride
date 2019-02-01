package cs340.TicketToRide;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

import static org.junit.jupiter.api.Assertions.*;

class ServerFacadeTest {

    private IServer server = ServerFacade.getInstance();
    private IServerModel model = ServerModel.getInstance();

    @Test
    void login() {
        Username username = new Username("nate");
        Password password = new Password("1234");
        boolean success = true;
        try {
            AuthToken token = server.register(username, password);
            assertNotNull(token);
            User user = model.getUserByAuthToken(token);
            assertEquals(user.getUsername(), username);
            assertEquals(user.getPassword(), password);

            token = server.login(username, password);
            assertNotNull(token);
            user = model.getUserByAuthToken(token);
            assertEquals(user.getPassword(), password);
            assertEquals(user.getPassword(), password);
        } catch (Exception e) {
            success = false;
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        assertTrue(success);
    }

    @Test
    void loginFail() {

    }

    @Test
    void register() {
    }

    @Test
    void createGame() {
    }

    @Test
    void joinGame() {
    }
}