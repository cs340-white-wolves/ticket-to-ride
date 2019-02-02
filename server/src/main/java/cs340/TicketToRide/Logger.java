package cs340.TicketToRide;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {

    public static java.util.logging.Logger logger;

    static {
        try {
            initLog();
        }
        catch (IOException e) {
            System.out.println("Could not initialize logger: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initLog() throws IOException {
        Level logLevel = Level.FINEST;

        logger = java.util.logging.Logger.getLogger("tickettoride");
        logger.setLevel(logLevel);
        logger.setUseParentHandlers(false);

        java.util.logging.Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

        FileHandler fileHandler = new FileHandler("server_log.txt", false);
        fileHandler.setLevel(logLevel);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }
}
