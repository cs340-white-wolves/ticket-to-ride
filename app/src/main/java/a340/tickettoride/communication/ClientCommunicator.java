package a340.tickettoride.communication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.Gson;
import cs340.TicketToRide.communication.Response;
import cs340.TicketToRide.communication.ServerCommand;

public class ClientCommunicator {

    private static ClientCommunicator SINGLETON = null;
    private Gson gson = new Gson();

    private ClientCommunicator() {};

    public static ClientCommunicator getSingleton() {

        if (SINGLETON == null) {
            SINGLETON = new ClientCommunicator();
        }

        return SINGLETON;
    }

    public Response sendCommand(ServerCommand commandToSend) {

        final String TARGET_RECIPIENT = "http://localhost:8080/command";
        Response result = null;

        //Open the connection to the server
        HttpURLConnection connection = openConnection(TARGET_RECIPIENT);


        //Write the request object
        serializeCommand(connection, commandToSend);

        //Send and receive data
        connection.connect();

        //if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

        // Get the response from the server
        Reader reader = new InputStreamReader(connection.getInputStream());
        result = gson.fromJson(reader, Response.class);

        return result;
    }

    private HttpURLConnection openConnection(String recipient) throws IOException {
        URL url = new URL(recipient);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setDoOutput(true);
        return connection;
    }

    private void serializeCommand(HttpURLConnection connection, ServerCommand requestInfo) throws IOException {
        OutputStream reqBody = connection.getOutputStream();
        Writer writer = new OutputStreamWriter(reqBody);
        gson.toJson(requestInfo, writer);
        writer.close();
    }

    private Response deserializeResponse() {
        return null;
    }


}

