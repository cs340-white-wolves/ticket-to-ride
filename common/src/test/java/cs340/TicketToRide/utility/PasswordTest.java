package cs340.TicketToRide.utility;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void serializable () {
        Password password = new Password("test");

        Gson gson = new Gson();

        String json = gson.toJson(password);

        Password password1 = gson.fromJson(json, Password.class);

        assertEquals(password, password1);
    }
}