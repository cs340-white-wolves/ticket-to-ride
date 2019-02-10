package a340.tickettoride.communication;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import a340.tickettoride.ServerProxy;
import cs340.TicketToRide.model.Games;

public class Poller {

    private Listener listener;
    private ScheduledFuture scheduledFuture;
    public Poller(Listener listener) {
        this.listener = listener;
    }

    public void run() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = scheduler.scheduleAtFixedRate(new Thread() {
            @Override
            public void run() {
                ServerProxy server = ServerProxy.getInstance();
                Games games = server.getGameList();
                listener.onPollComplete(games);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public interface Listener {
        void onPollComplete(Games lobbyGameList);
    }
}
