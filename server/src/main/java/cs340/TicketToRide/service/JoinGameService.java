package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.utility.ID;

public class JoinGameService {
    public Game joinGame(AuthToken token, ID gameId) {
        if (token == null || gameId == null || !token.isValid() || !gameId.isValid()) {
            throw new IllegalArgumentException();
        }

        return null;
    }
}
