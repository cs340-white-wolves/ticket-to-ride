package cs340.TicketToRide;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

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
        new ServerCommunicator().run();
    }
}
