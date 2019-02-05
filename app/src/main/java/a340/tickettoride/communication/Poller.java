package a340.tickettoride.communication;

import android.os.AsyncTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import a340.tickettoride.ServerProxy;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.communication.PollerResponse;
import cs340.TicketToRide.model.Games;

public class Poller {

    private Listener listener;
    public Poller(Listener listener) {
        this.listener = listener;
    }

//    private class PollerTask extends AsyncTask<Void, Integer, PollerResponse> {
//
//        @Override
//        protected PollerResponse doInBackground(Void... voids) {
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(PollerResponse response) {
//
//        }
//    }

    public void run() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(new Thread() {
            @Override
            public void run() {
                ServerProxy server = ServerProxy.getInstance();
                server.poll();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public interface Listener {
        void onPollComplete(Games lobbyGameList);
    }
}
