package cs340.TicketToRide.model;

import java.util.HashMap;
import java.util.Map;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.utility.ID;

public class ClientQueueManager {
    private Map<ID, ClientCommandQueue> queues = new HashMap<>();

    public void create(ID playerId) {
        queues.put(playerId, new ClientCommandQueue());
    }

    public void put(ID playerId, Command cmd) {
        ClientCommandQueue ccq = queues.get(playerId);

        if (ccq == null) {
            throw new RuntimeException("Could not put into non-existent queue");
        }

        ccq.add(cmd);
    }

    public Commands getAfter(ID playerId, int index) {
        ClientCommandQueue ccq = queues.get(playerId);

        if (ccq == null) {
            throw new RuntimeException("Could not put into non-existent queue");
        }

        return ccq.getAfter(index);
    }
}
