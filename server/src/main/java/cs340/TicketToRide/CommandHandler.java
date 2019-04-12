package cs340.TicketToRide;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Response;
import cs340.TicketToRide.model.ClientProxyManager;
import cs340.TicketToRide.model.ServerModel;
import cs340.TicketToRide.model.db.IGameDao;
import cs340.TicketToRide.model.db.IUserDao;

public class CommandHandler implements HttpHandler{
    private int delta;
    private int cmdsStored;
    private Gson gson = new Gson();
    private List<String> cmdsToExclude = new ArrayList<>();

    public CommandHandler(int delta) {
        this.delta = delta;
        cmdsStored = 0;
        cmdsToExclude.add("getQueuedCommands");
        cmdsToExclude.add("getAvailableGames");
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (! httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendError(httpExchange, 405, "Method Not Supported");
            return;
        }

        Command cmd = decode(httpExchange);
        storeCommand(cmd);
        Object obj = cmd.execute(ServerFacade.getInstance());

        ServerModel model = ServerModel.getInstance();
        if (model.isWasRandomized()) {
            forceStoreState();
            model.setWasRandomized(false);
        }

        encodeAndSend(httpExchange, obj);
    }

    private void storeCommand(Command cmd) {
        if (cmd == null) {
            return;
        }
        String methodName = cmd.getMethodName();
        if (!cmdsToExclude.contains(methodName)) {
            ServerModel model = ServerModel.getInstance();
            IGameDao gameDao = model.getGameDao();
            IUserDao userDao = model.getUserDao();
            gameDao.beginTransaction();
            userDao.beginTransaction();
            if (cmdsStored == delta) {
                saveFull(model, gameDao, userDao);
            }
            gameDao.saveCommand(cmd);
            userDao.endTransaction();
            gameDao.endTransaction();
            cmdsStored++;
        }

    }

    private void forceStoreState() {
        ServerModel model = ServerModel.getInstance();
        IGameDao gameDao = model.getGameDao();
        IUserDao userDao = model.getUserDao();
        gameDao.beginTransaction();
        userDao.beginTransaction();
        saveFull(model, gameDao, userDao);
        userDao.endTransaction();
        gameDao.endTransaction();
    }

    private void saveFull(ServerModel model, IGameDao gameDao, IUserDao userDao) {
        gameDao.clearCommands();
        gameDao.saveGames(model.getGames());
        gameDao.saveClientManager(ClientProxyManager.getInstance());
        userDao.saveUsers(model.getUsers());
        userDao.saveTokens(model.getAuthManager());
        cmdsStored = 0;
    }

    private void encodeAndSend (HttpExchange httpExchange, Object obj) {
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());

        try {
            Response response;
            if (obj instanceof Exception) {
                response = new Response((Exception) obj);
            } else {
                response = new Response(obj);
            }
            String jsonStr = gson.toJson(response);

            //System.out.println(jsonStr);
            httpExchange.sendResponseHeaders(200, jsonStr.length());

            osw.write(jsonStr);
            osw.close();

            httpExchange.close();
        } catch (IOException e) {
            Logger.logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private Command decode (HttpExchange httpExchange) {
        InputStream requestBody = httpExchange.getRequestBody();
        InputStreamReader reqSR = new InputStreamReader(requestBody);

        try {
            return new Command(reqSR);
        }
        catch (JsonParseException e) {
            Logger.logger.log(Level.WARNING, e.getMessage(), e);
        }

        return null;
    }

    private void sendError(HttpExchange httpExchange, int err, String msg) throws IOException {
        String realMsg = String.format(Locale.US,"<h1>%d %s</h1>", err, msg);
        httpExchange.sendResponseHeaders(err, realMsg.length());
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());
        osw.write(realMsg);
        osw.close();
    }
}
