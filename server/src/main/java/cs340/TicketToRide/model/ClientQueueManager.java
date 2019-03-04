package cs340.TicketToRide.model;

import java.util.HashMap;
import java.util.Map;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.utility.ID;

public class ClientQueueManager {
    private static ClientQueueManager singleton;

    private Map<ID, ClientCommandQueue> queues = new HashMap<>();

    public void create(Player player) {
        queues.put(player.getId(), new ClientCommandQueue());
    }

    public void put(Player player, Command cmd) {
        ClientCommandQueue ccq = queues.get(player.getId());

        if (ccq == null) {
            throw new RuntimeException("Could not put into non-existent queue");
        }

        ccq.add(cmd);
    }

    public Commands getAfter(Player player, int index) {
        ClientCommandQueue ccq = queues.get(player.getId());

        if (ccq == null) {
            throw new RuntimeException("Could not put into non-existent queue");
        }

        return ccq.getAfter(index);
    }
}
