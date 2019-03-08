package cs340.TicketToRide.model;

import java.util.List;
import java.util.ArrayList;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;

public class ClientCommandQueue {

    private List<Command> commands = new ArrayList<>();

    public void add(Command cmd) {
        commands.add(cmd);
    }

    public Commands getStartingFrom(int index) {
        int size = commands.size();

        Commands cmds = new Commands(size - 1);

        for (int i = index + 1; i < size; i++) {
            cmds.add(commands.get(i), 1);
        }

        return cmds;
    }
}
