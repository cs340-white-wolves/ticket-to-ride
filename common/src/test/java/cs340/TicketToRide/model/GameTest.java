package cs340.TicketToRide.model;

import com.google.gson.Gson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void serializable() throws Exception {
        Gson gson = new Gson();

        Game game = new Game(5);
        String json = gson.toJson(game);
        Game game2 = gson.fromJson(json, Game.class);

        assertEquals(game, game2);
    }
}