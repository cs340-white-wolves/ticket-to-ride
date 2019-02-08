package cs340.TicketToRide.utility;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IDTest {
    @Test
    void serializable () {
        ID id = ID.generateID();

        Gson gson = new Gson();

        String json = gson.toJson(id);

        ID id2 = gson.fromJson(json, ID.class);

        assertEquals(id, id2);
    }
}