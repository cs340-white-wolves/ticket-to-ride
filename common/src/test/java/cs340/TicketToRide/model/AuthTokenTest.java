package cs340.TicketToRide.model;

import com.google.gson.Gson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenTest {
    @Test
    void serializable() throws Exception {
        Gson gson = new Gson();

        AuthToken origToken = AuthToken.generateToken();
        String json = gson.toJson(origToken);
        AuthToken desToken = gson.fromJson(json, AuthToken.class);

        assertEquals(origToken, desToken);
    }
}