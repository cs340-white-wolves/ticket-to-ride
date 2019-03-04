package cs340.TicketToRide.communication;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    private List<Command> commands;

    private int startIndex = Integer.MAX_VALUE;
    private int endIndex = 0;

    public Commands() {
        commands = new ArrayList<Command>();
    }

    public void add(Command cmd, int index) {
        commands.add(cmd);

        if (startIndex > index) {
            startIndex = index;
        }

        if (endIndex < index) {
            endIndex = index;
        }
    }

    public List<Command> getAll () {
        return commands;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}