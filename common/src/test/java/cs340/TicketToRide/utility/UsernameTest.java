package cs340.TicketToRide.utility;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {
    @Test
    void serializable () {
        Username username = new Username("test");

        Gson gson = new Gson();

        String json = gson.toJson(username);

        Username username1 = gson.fromJson(json, Username.class);

        assertEquals(username, username1);
    }

}