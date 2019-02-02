package cs340.TicketToRide;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {

    public static void main(String[] args) {
        int port = getPort(args);
        run(port);
    }

    private static int getPort(String[] args) {
        int port = 8080;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return port;
    }

    private static void run(int port) {
        try {
            System.out.println("server listening on port: " + port);
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/", new Handler());
            // todo: Maybe create default handler for empty path?
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
