package cs340.TicketToRide;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.logging.Level;

import cs340.TicketToRide.communication.Command;

public class Handler implements HttpHandler{
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (! httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendError(httpExchange, 405, "Method Not Supported");
            return;
        }

        Command cmd = decode(httpExchange);

        Object obj = cmd.execute();

        encodeAndSend(httpExchange, obj);
    }

    private void encodeAndSend (HttpExchange httpExchange, Object obj) {
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());

        try {
            String jsonStr = gson.toJson(obj);

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
            return gson.fromJson(reqSR, Command.class);
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
