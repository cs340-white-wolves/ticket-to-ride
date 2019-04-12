package cs340.TicketToRide;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.*;

import cs340.TicketToRide.model.db.IDaoFactory;

public class ServerCommunicator {

    private static final int MAX_CONNECTIONS = 10;
    private static final int PORT = 8080;
    private static final String PATH_COMMAND = "/command";
    private static int deltas = 0;

    private void run () {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(PORT), MAX_CONNECTIONS);
        }
        catch (IOException e) {
            Logger.logger.log(Level.SEVERE, e.getMessage(), e);
            return;
        }

//        server.setExecutor(null);

        Logger.logger.info("Creating context");
        server.createContext(PATH_COMMAND, new CommandHandler(deltas));

        Logger.logger.info("Starting HTTP server");
        server.start();
    }

    public static void main(String[] args) {
        ServerCommunicator server = new ServerCommunicator();

        try {
            server.checkParameters(args);
            PersistencePluginManager ppm = PersistencePluginManager.getInstance();
            IDaoFactory flatfile = ppm.createPluginFactory(args[0]);

            if (flatfile == null) {
                System.exit(10);
            }

        } catch (RuntimeException e) {
            System.out.println("Invalid parameters");
            server.usage();
            System.exit(1);
        }

        server.run();
    }

    private void checkParameters(String[] args) {
        if (args.length != 2) {
            throw new RuntimeException("Invalid parameters");
        }

        deltas = Integer.valueOf(args[1]);
    }

    private void usage() {
        System.out.println("USAGE: java ServerCommunicator <Persistence Plugin> <Number of Deltas>");
    }

}
