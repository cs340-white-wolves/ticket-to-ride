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
    private List<String> cmdsToDelta = new ArrayList<>();
    private List<String> gameCmdsToForce = new ArrayList<>();
    private List<String> userCmdsToForce = new ArrayList<>();

    public CommandHandler(int delta) {
        this.delta = delta;
        cmdsStored = 0;

        cmdsToDelta.add("sendChat");
        cmdsToDelta.add("discardDestCards");
        cmdsToDelta.add("claimRoute");
        cmdsToDelta.add("drawTrainCard");
        cmdsToDelta.add("drawDestCards");

        gameCmdsToForce.add("createGame");
        gameCmdsToForce.add("joinGame");

        userCmdsToForce.add("register");
        userCmdsToForce.add("login");
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

        String cmdMethod = cmd.getMethodName();

        ServerModel model = ServerModel.getInstance();


        if (model.isWasRandomized() || gameCmdsToForce.contains(cmdMethod)) {
            forceStoreGameState();
            model.setWasRandomized(false);
        }

        if (userCmdsToForce.contains(cmdMethod)) {
            saveFullUsers(model, model.getUserDao());
        }

        encodeAndSend(httpExchange, obj);
    }

    private void storeCommand(Command cmd) {
        if (cmd == null) {
            return;
        }
        String methodName = cmd.getMethodName();

        if (cmdsToDelta.contains(methodName)) {
            ServerModel model = ServerModel.getInstance();
            IGameDao gameDao = model.getGameDao();
            gameDao.beginTransaction();
            if (cmdsStored == delta) {
                saveFullGames(model, gameDao);
            }
            gameDao.saveCommand(cmd);
            gameDao.endTransaction();
            cmdsStored++;
        }

    }

    private void forceStoreGameState() {
        ServerModel model = ServerModel.getInstance();
        IGameDao gameDao = model.getGameDao();
        gameDao.beginTransaction();
        saveFullGames(model, gameDao);
        gameDao.endTransaction();
    }

    private void saveFullGames(ServerModel model, IGameDao gameDao) {
        gameDao.clearCommands();
        gameDao.saveGames(model.getGames());
        gameDao.saveClientManager(ClientProxyManager.getInstance());
        cmdsStored = 0;
    }

    private void saveFullUsers(ServerModel model, IUserDao userDao) {
        userDao.beginTransaction();
        userDao.saveUsers(model.getUsers());
        userDao.saveTokens(model.getAuthManager());
        userDao.endTransaction();
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
