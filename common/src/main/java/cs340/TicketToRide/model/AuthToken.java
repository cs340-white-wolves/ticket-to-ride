package cs340.TicketToRide.model;


import java.util.UUID;

import cs340.TicketToRide.utility.ID;

public class AuthToken {
    private static final int TOKEN_LENGTH = 8;
    private String token;

    public AuthToken(String token) {
        setToken(token);
    }

    private void setToken(String token) {
        if (token == null || token.equals("")) {
            throw new IllegalArgumentException();
        }
        this.token = token;
    }

    public boolean isValid() {
        return this.token != null && !this.token.equals("");
    }

    public String toString() {
        return this.token;
    }

    public static AuthToken generateToken() {
        String token = UUID.randomUUID().toString().substring(0, TOKEN_LENGTH);
        return new AuthToken(token);
    }
}
