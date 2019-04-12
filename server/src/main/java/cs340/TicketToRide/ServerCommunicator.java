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
            IDaoFactory factory = ppm.createPluginFactory(args[0]);

            if (factory == null) {
                System.exit(10);
            }

            IGameDao gameDao = factory.createGameDao();
            IUserDao userDao = factory.createUserDao();

            ServerModel model = ServerModel.getInstance();
            model.setGameDao(gameDao);
            model.setUserDao(userDao);

            loadData();
            runStoredCmds(gameDao.loadCommands());

        } catch (RuntimeException e) {
            System.out.println("Invalid parameters");
            server.usage();
            System.exit(1);
        }

        server.run();
    }

    private static void loadData() {
        ServerModel model = ServerModel.getInstance();
        IGameDao gameDao = model.getGameDao();
        IUserDao userDao = model.getUserDao();
        Games games = gameDao.loadGames();
        ClientProxyManager manager = gameDao.loadClientManager();

        Set<User> users = userDao.loadUsers();
        AuthManager authManager = userDao.loadTokens();

        model.setGames(games);
        model.setUsers(users);
        model.setAuthManager(authManager);
        ClientProxyManager.setSingleton(manager);
    }

    private static void runStoredCmds(Commands commands) {
        for (Command command : commands.getAll()) {
            command.execute(ServerFacade.getInstance());
        }
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
