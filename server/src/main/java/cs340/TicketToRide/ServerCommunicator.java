package cs340.TicketToRide;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

import cs340.TicketToRide.communication.ServerCommand;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;


public class ServerCommunicator {

    private static final int MAX_CONNECTIONS = 10;
    private static final int PORT = 8080;
    private static final String PATH_COMMAND = "/command";

    private void run () {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(PORT), MAX_CONNECTIONS);
        }
        catch (IOException e) {
            Logger.logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

        server.setExecutor(null);

        Logger.logger.info("Creating context");
        server.createContext(PATH_COMMAND, new Handler());

        Logger.logger.info("Starting HTTP server");
        server.start();
    }

    public static void main(String[] args) {
//        new ServerCommunicator().run();
        Class<?>[] paramTypes = {Username.class, Password.class};
        Object[] params = {new Username("nate"), new Password("1234")};
        ServerCommand command = new ServerCommand("register", paramTypes, params);
        Object result = command.execute(ServerFacade.getInstance());
        System.out.println("here");
    }
}
