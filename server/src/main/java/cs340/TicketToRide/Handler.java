package cs340.TicketToRide;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class Handler implements HttpHandler{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestMethod() != "POST") {
            sendError(httpExchange, 405, "Method Not Supported");
            return;
        }

        // TODO: stub
    }

    private void sendError(HttpExchange httpExchange, int err, String msg) throws IOException {
        String realMsg = String.format(Locale.US,"<h1>%d %s</h1>", err, msg);
        httpExchange.sendResponseHeaders(err, realMsg.length());
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());
        osw.write(realMsg);
        osw.close();
    }
}
