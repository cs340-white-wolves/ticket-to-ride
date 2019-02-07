package cs340.TicketToRide;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

import static org.junit.jupiter.api.Assertions.*;

class ServerFacadeTest {

    private IServer server = ServerFacade.getInstance();
    private IServerModel model = ServerModel.getInstance();

    @AfterEach
    void clearData() {
        model.clear();
    }

    @Test
    void login() {
        Username username = new Username("nate");
        Password password = new Password("1234");
        boolean success = true;
        try {
            LoginRegisterResponse response = server.register(username, password);
            AuthToken token = response.getToken();
            assertNotNull(token);
            assertTrue(token.isValid());
            User user = model.getUserByAuthToken(token);
            assertTrue(user.isValid());
            assertEquals(user.getUsername(), username);
            assertEquals(user.getPassword(), password);

            token = server.login(username, password).getToken();
            assertNotNull(token);
            assertTrue(token.isValid());
            user = model.getUserByAuthToken(token);
            assertTrue(user.isValid());
            assertEquals(user.getPassword(), password);
            assertEquals(user.getPassword(), password);
        } catch (Exception e) {
            success = false;
            System.out.println(e.getMessage());
        }

        assertTrue(success);
    }

    @Test
    void loginFail() {
        Username username = new Username("nate");
        Password password = new Password("1234");
        boolean success = true;
        AuthToken token;
        try {
            // Attempt to login with non registered user
            token = server.login(username, password).getToken();
        } catch (Exception e) {
            success = false;
            System.out.println(e.getMessage());
        }
        assertFalse(success);

        success = true;
        try {
            // This time, register user, but use wrong password in attempt to login
            token = server.register(username, password).getToken();
            assertNotNull(token);
            token = server.login(username, new Password("abcd")).getToken();
        } catch (Exception e) {
            success = false;
        }
        assertFalse(success);
    }

    @Test
    void register() {
    }

    @Test
    void registerFail() {

    }

    @Test
    void createGame() {
        boolean success = true;
        int numPlayers = 3;
        Game game;
        try {
            AuthToken token = server.register(new Username("nate"), new Password("1234")).getToken();
            assertNotNull(token);
            game = server.createGame(token, numPlayers);
            assertNotNull(game);
            assertTrue(game.isValid());
        } catch (Exception e) {
            success = false;
            System.out.println(e.getMessage());
        }

        assertTrue(success);
    }

    @Test
    void createGameFail() {

    }

    @Test
    void joinGame() {
        boolean success = true;
        int numPlayers = 3;
        Game game;
        try {
            AuthToken token = server.register(new Username("nate"), new Password("1234")).getToken();
            assertNotNull(token);
            game = server.createGame(token, numPlayers);
            assertNotNull(game);
            assertTrue(game.isValid());
            assertFalse(game.hasTargetNumPlayers());
            assertEquals(game.getNumCurrentPlayers(), 1);

            token = server.register(new Username("jake"), new Password("abcd")).getToken();
            assertNotNull(token);
            Game joinedGame = server.joinGame(token, game.getGameID());
            assertNotNull(joinedGame);
            assertFalse(game.hasTargetNumPlayers());
            assertEquals(game.getNumCurrentPlayers(), 2);

            token = server.register(new Username("sam"), new Password("pass")).getToken();
            assertNotNull(token);
            joinedGame = server.joinGame(token, game.getGameID());
            assertNotNull(joinedGame);
            assertTrue(game.hasTargetNumPlayers());
            assertEquals(game.getNumCurrentPlayers(), numPlayers);
        } catch (Exception e) {
            success = false;
            System.out.println(e.getMessage());
        }

        assertTrue(success);
    }
}