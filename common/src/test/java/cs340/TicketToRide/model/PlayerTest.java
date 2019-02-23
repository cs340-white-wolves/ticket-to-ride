package cs340.TicketToRide.model;

import com.google.gson.Gson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void serializable () {
        Gson gson = new Gson();

        User user = new User(new Username("test"), new Password("test"));
        Player player = new Player(user);

        String json = gson.toJson(player);

        Player player1 = gson.fromJson(json, Player.class);

        assertEquals(player, player1);
    }
}