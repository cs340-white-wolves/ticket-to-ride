package cs340.TicketToRide;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.communication.LoginRegisterResponse;
import cs340.TicketToRide.exception.AuthenticationException;
import cs340.TicketToRide.exception.NotUniqueException;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.IServerModel;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class ServerFacadeTest {

    private IServer server = ServerFacade.getInstance();
    private IServerModel model = ServerModel.getInstance();
    private Username username = new Username("nate");
    private Password password = new Password("1234");

    @After
    public void clearData() {
        model.clear();
    }

    private void createUser() throws NotUniqueException {
        LoginRegisterResponse register = server.register(username, password);

        assertNotNull(register);

        User user = register.getUser();
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());

        AuthToken token = register.getToken();
        assertNotNull(token);
        assertNotEquals("", token.toString());
    }

    @Test
    public void loginSuccess() throws Exception {
        createUser();

        LoginRegisterResponse login = server.login(username, password);
        assertNotNull(login);

        User user = login.getUser();
        assertNotNull(user);
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    @Test(expected = AuthenticationException.class)
    public void loginWrongPassword() throws Exception {
        createUser();

        server.login(username, new Password("notpassword"));
    }

    @Test(expected = AuthenticationException.class)
    public void loginNonExistent() throws Exception {
        createUser();

        server.login(new Username("nobody"), new Password("notpassword"));
    }

    @Test
    public void loginFail() {
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
    public void registerSuccess() throws Exception {
        createUser();
    }

    @Test
    public void registerNull() throws Exception {
        try {
            server.register(null, null);
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            server.register(new Username("test"), null);
            fail();
        }
        catch (IllegalArgumentException e) {}

        try {
            server.register(null, new Password("test"));
            fail();
        }
        catch (IllegalArgumentException e) {}
    }

    @Test(expected = NotUniqueException.class)
    public void registerAlreadyExists() throws Exception {
        server.register(username, password);
        server.register(username, password);
    }

    @Test
    public void createGame() throws Exception {
        createUser();

        LoginRegisterResponse resp = server.login(username, password);
        AuthToken token = resp.getToken();

        for (int numPlayers = 3; numPlayers <= 5; numPlayers++) {
            Game game = server.createGame(token, numPlayers);
            assertNotNull(game);
            assertTrue(game.isValid());
            assertEquals(numPlayers, game.getTargetNumPlayers());
            assertEquals(1, game.getNumCurrentPlayers());
            assertEquals(username, game.getCreator());
        }
    }

    @Test
    public void createGameFail() throws Exception {
        createUser();

        LoginRegisterResponse resp = server.login(username, password);
        AuthToken token = resp.getToken();

        Set<Integer> cases = new HashSet<>();
        cases.add(-1);
        cases.add(0);
        cases.add(1);
        cases.add(6);
        cases.add(1000);

        for (Integer theCase : cases) {
            try {
                server.createGame(token, theCase);
                fail("Game should not be able to have " + theCase + " players");
            }
            catch (IllegalArgumentException e) {}
        }
    }

    @Test
    public void joinGameSuccess() throws Exception {
        LoginRegisterResponse host = server.register(new Username("player1"), new Password("1"));
        Username hostUsername = host.getUser().getUsername();
        AuthToken p1tok = host.getToken();
        AuthToken p2tok = server.register(new Username("player2"), new Password("2")).getToken();
        AuthToken p3tok = server.register(new Username("player3"), new Password("3")).getToken();

        int numPlayers = 3;

        Game gameHost = server.createGame(p1tok, numPlayers);
        assertNotNull(gameHost);
        assertNotNull(gameHost.getGameID());
        assertEquals(numPlayers, gameHost.getTargetNumPlayers());
        assertEquals(1, gameHost.getNumCurrentPlayers());
        assertEquals(hostUsername, gameHost.getCreator());

        Game game2 = server.joinGame(p2tok, gameHost.getGameID());
        assertNotNull(game2);
        assertEquals(gameHost, game2);
        assertEquals(numPlayers, game2.getTargetNumPlayers());
        assertEquals(2, gameHost.getNumCurrentPlayers());

        Game game3 = server.joinGame(p3tok, gameHost.getGameID());
        assertNotNull(game3);
        assertEquals(gameHost, game3);
        assertEquals(numPlayers, game3.getTargetNumPlayers());
        assertEquals(3, gameHost.getNumCurrentPlayers());
    }
}