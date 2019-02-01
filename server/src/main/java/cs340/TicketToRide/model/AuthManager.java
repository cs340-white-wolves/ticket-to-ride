package cs340.TicketToRide.model;

import java.util.Map;
import java.util.TreeMap;

public class AuthManager {
    private Map<AuthToken, User> tokenUserMap;
    public AuthManager() {
        tokenUserMap = new TreeMap<>();
    }

    public void addTokenUser(AuthToken token, User user) throws Exception {
        if (token == null || user == null || !token.isValid() || !user.isValid()) {
            throw new IllegalArgumentException();
        }

        if (tokenUserMap == null) {
            return;
        }

        if (tokenUserMap.get(token) != null) {
            throw new Exception("This auth token already belongs to a user");
        }

        tokenUserMap.put(token, user);
    }

    public User getUserByAuthToken(AuthToken token) {
        if (token == null || !token.isValid()) {
            throw new IllegalArgumentException();
        }

        if (tokenUserMap == null) {
            return null;
        }

        return tokenUserMap.get(token);
    }
}
