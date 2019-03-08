package cs340.TicketToRide.communication;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    private List<Command> commands;

    private int startIndex = 0;
    private int endIndex = 0;

    public Commands(int endIndex) {
        commands = new ArrayList<Command>();
        this.endIndex = endIndex;
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
