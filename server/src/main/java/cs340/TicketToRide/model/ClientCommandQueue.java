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

    public Commands getAfter(int index) {
        int size = commands.size();

        Commands cmds = new Commands();

        for (int i = index + 1; i < size; i++) {
            commands.add(i, commands.get(i));
        }

        return cmds;
    }
}
