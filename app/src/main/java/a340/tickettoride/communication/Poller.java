package a340.tickettoride.communication;


import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.model.Games;

public class Poller {
    public static final int POLLER_FREQUENCY = 1; // in seconds
    private Listener listener;
    private ScheduledFuture scheduledFuture;

    public Poller(Listener listener) {
        Log.i("Poller", "Constructor");
        this.listener = listener;
    }

    public void run() {
        Log.i("Poller", "->run()");
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = scheduler.scheduleAtFixedRate(new Thread() {
            @Override
            public void run() {
                try {
                    Log.i("Poller", "fixedRate run()");
                    IClientModel model = ClientModel.getInstance();
                    ServerProxy server = ServerProxy.getInstance();
                    Games games = server.getAvailableGames(model.getAuthToken());
                    Log.i("Poller", "Got " + games.getGameSet().size() + " games");
                    listener.onPollComplete(games);
                }
                catch (Throwable t) {
                    t.printStackTrace();
                    Log.e("Poller", t.getMessage());
                }
            }
        }, 0, POLLER_FREQUENCY, TimeUnit.SECONDS);
    }

    public interface Listener {
        void onPollComplete(Games lobbyGameList);
    }
}
