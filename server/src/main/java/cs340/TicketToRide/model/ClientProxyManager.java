package cs340.TicketToRide.model;

import java.util.HashMap;
import java.util.Map;

import cs340.TicketToRide.ClientProxy;
import cs340.TicketToRide.utility.ID;

public class ClientProxyManager {
    private transient static ClientProxyManager singleton;

    private ClientProxyManager() {
    }

    public static ClientProxyManager getInstance() {
        if (singleton == null) {
            singleton = new ClientProxyManager();
        }

        return singleton;
    }

    private Map<String, ClientProxy> proxies = new HashMap<>();

    public void create(ID playerId) {
        proxies.put(playerId.toString(), new ClientProxy());
    }

    public ClientProxy get(ID playerId) {
        return proxies.get(playerId.toString());
    }

    public static void setSingleton(ClientProxyManager singleton) {
        ClientProxyManager.singleton = singleton;
    }

    public void clear() {
        proxies.clear();
    }
}
