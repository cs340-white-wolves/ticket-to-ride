package cs340.TicketToRide.model;

import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private Map<String, User> tokenUserMap;
    public AuthManager() {
        tokenUserMap = new HashMap<>();
    }

    public void addTokenUser(AuthToken token, User user) {
        if (token == null || user == null || !token.isValid() || !user.isValid()) {
            throw new IllegalArgumentException();
        }

        if (tokenUserMap == null) {
            return;
        }

        if (tokenUserMap.get(token.toString()) != null) {
            throw new RuntimeException("This auth token already belongs to a user");
        }

        tokenUserMap.put(token.toString(), user);
    }

    public User getUserByAuthToken(AuthToken token) {
        if (token == null || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (tokenUserMap == null) {
            return null;
        }

        return tokenUserMap.get(token.toString());
    }

    public void clear() {
        if (tokenUserMap == null) {
            return;
        }
        tokenUserMap.clear();
    }
}
