package cs340.TicketToRide;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.logging.*;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.AuthManager;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.db.IDaoFactory;
import cs340.TicketToRide.model.db.IGameDao;
import cs340.TicketToRide.model.db.IUserDao;

public class ServerCommunicator {

    private static final int MAX_CONNECTIONS = 10;
    private static final int PORT = 8080;
    private static final String PATH_COMMAND = "/command";
    private static int deltas = 0;
    private static boolean clear = false;

    private void run() {
        HttpServer server;

        try {
            server = HttpServer.create(new InetSocketAddress(PORT), MAX_CONNECTIONS);
        } catch (IOException e) {
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

        ServerModel model = ServerModel.getInstance();
        try {
            server.checkParameters(args);
        } catch (RuntimeException e) {
            System.out.println("Invalid parameters");
            server.usage();
            System.exit(1);
        }

        try {
            PersistencePluginManager ppm = PersistencePluginManager.getInstance();
            IDaoFactory factory = ppm.createPluginFactory(args[0]);

            if (factory == null) {
                System.exit(10);
            }

            IGameDao gameDao = factory.createGameDao();
            IUserDao userDao = factory.createUserDao();

            model.setGameDao(gameDao);
            model.setUserDao(userDao);

            if (clear) {
                clearData();
            } else {
                Commands commands = loadData();
                runStoredCmds(commands);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(10);
        }

        server.run();
    }

    private static void clearData() {
        ServerModel model = ServerModel.getInstance();
        IGameDao gameDao = model.getGameDao();
        IUserDao userDao = model.getUserDao();

        gameDao.beginTransaction();
        userDao.beginTransaction();

        userDao.clearAll();
        gameDao.clearAll();

        userDao.endTransaction();
        gameDao.endTransaction();
    }

    private static Commands loadData() {
        ServerModel model = ServerModel.getInstance();
        IGameDao gameDao = model.getGameDao();
        IUserDao userDao = model.getUserDao();

        gameDao.beginTransaction();
        userDao.beginTransaction();

        Games games = gameDao.loadGames();
        ClientProxyManager manager = gameDao.loadClientManager();
        Commands commands = gameDao.loadCommands();
        Set<User> users = userDao.loadUsers();
        AuthManager authManager = userDao.loadTokens();

        userDao.endTransaction();
        gameDao.endTransaction();

        if (games != null) {
            model.setGames(games);
        }

        if (users != null) {
            model.setUsers(users);
        }

        if (authManager != null) {
            model.setAuthManager(authManager);
        }

        if (manager != null) {
            ClientProxyManager.setSingleton(manager);
        }

        return commands;
    }

    private static void runStoredCmds(Commands commands) {
        if (commands == null) {
            return;
        }

        for (Command command : commands.getAll()) {
            command.execute(ServerFacade.getInstance());
        }
    }

    private void checkParameters(String[] args) {
        if (args.length != 2 && args.length != 3) {
            throw new RuntimeException("Invalid parameters");
        }

        deltas = Integer.valueOf(args[1]);
        clear = args.length == 3 && (args[2].equals("-clear"));
    }

    private void usage() {
        System.out.println("USAGE: java ServerCommunicator <Persistence Plugin> <Number of Deltas>");
    }

}
