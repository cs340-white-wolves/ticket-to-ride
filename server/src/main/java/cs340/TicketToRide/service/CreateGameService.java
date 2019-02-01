package cs340.TicketToRide.service;

import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.Game;

public class CreateGameService {
    public Game createGame(AuthToken token) {
        if (token == null || !token.isValid()) {
            throw new IllegalArgumentException();
        }
        return null;
    }
}
