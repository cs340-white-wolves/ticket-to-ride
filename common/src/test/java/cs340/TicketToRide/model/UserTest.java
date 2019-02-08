package cs340.TicketToRide.model;

import com.google.gson.Gson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void serializable () {
        User user = new User(new Username("test"), new Password("test"));

        Gson gson = new Gson();

        String json = gson.toJson(user);

        User user1 = gson.fromJson(json, User.class);

        assertEquals(user, user1);
    }
}