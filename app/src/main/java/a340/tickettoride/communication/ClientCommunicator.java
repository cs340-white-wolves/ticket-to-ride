package a340.tickettoride.communication;

import com.google.gson.Gson;

import cs340.TicketToRide.communication.ICommand;
import cs340.TicketToRide.communication.Response;

public class ClientCommunicator {
    private static ClientCommunicator singleton;
    private Gson gson = new Gson();

    private ClientCommunicator() {
    }

    public static ClientCommunicator getInstance() {
        if (singleton == null) {
            singleton = new ClientCommunicator();
        }
        return singleton;
    }

    public Response sendCommand(ICommand command) {
        return null;
    }

    private String encodeCommand(ICommand command) {
        return null;
    }

    private Response decodeResponse(String jsonResponse) {
        return null;
    }

}
