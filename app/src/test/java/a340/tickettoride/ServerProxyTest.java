package a340.tickettoride;

import org.junit.jupiter.api.Test;

import a340.tickettoride.communication.ClientCommunicator;
import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.communication.Response;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;


import static org.junit.jupiter.api.Assertions.*;

class ServerProxyTest {

    @Test
    void login() throws Exception {
        Username myUsername = new Username("curtGilbert");
        Password myPassword = new Password("Password1");


        LoginRegisterResponse result = ServerProxy.getInstance().login(myUsername, myPassword);
        assertNotNull(result.getToken());
        assertEquals(result.getUser().getUsername().toString(), myUsername.toString());



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