package cs340.TicketToRide;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.logging.Level;

import cs340.TicketToRide.communication.Command;
import cs340.TicketToRide.communication.Response;

public class CommandHandler implements HttpHandler{
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (! httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendError(httpExchange, 405, "Method Not Supported");
            return;
        }

        Command cmd = decode(httpExchange);
        Object obj = cmd.execute(ServerFacade.getInstance());
        encodeAndSend(httpExchange, obj);
    }

    private void encodeAndSend (HttpExchange httpExchange, Object obj) {
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());

        try {
            Response response;
            if (obj instanceof Exception) {
                response = new Response((Exception) obj);
            } else {
                response = new Response(obj, obj.getClass().getName());
            }
            String jsonStr = gson.toJson(response);

            System.out.println(jsonStr);
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