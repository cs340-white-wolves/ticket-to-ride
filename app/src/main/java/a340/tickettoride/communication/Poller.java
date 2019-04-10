package a340.tickettoride.communication;


import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.communication.Commands;
import cs340.TicketToRide.model.Games;

public class Poller {
    public static final int POLLER_FREQUENCY = 1; // in seconds
    private Listener listener;
    private ScheduledFuture scheduledFuture;

    public Poller(Listener listener) {
        Log.i("Poller", "Constructor");
        this.listener = listener;
    }

    public void runUpdateGameList() {
        Log.i("Poller", "->runUpdateGameList()");
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = scheduler.scheduleAtFixedRate(new Thread() {
            @Override
            public void run() {
                try {
                    Log.i("Poller", "fixedRate runUpdateGameList()");
                    IClientModel model = ClientModel.getInstance();
                    ServerProxy server = ServerProxy.getInstance();
                    Games games = server.getAvailableGames(model.getAuthToken());
                    Log.i("Poller", "Got " + games.getGameSet().size() + " games");
                    listener.onPollComplete(games);
                } catch (Throwable t) {
                    t.printStackTrace();
                    Log.e("Poller", t.getMessage());
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void runGetGameCommands() {
        Log.i("Poller", "->runGetGameCommands()");
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = scheduler.scheduleAtFixedRate(new Thread() {
            @Override
            public void run() {
                try {
                    Log.i("Poller", "fixedRate runGetGameCommands()");
                    IClientModel model = ClientModel.getInstance();
                    IServer server = ServerProxy.getInstance();
                    int currentIndex = model.getLastExecutedCommandIndex();
                    Commands queuedCommands = server.getQueuedCommands(
                            model.getAuthToken(),
                            model.getPlayerId(),
                            model.getActiveGame().getGameID(),
                            currentIndex);
                    Log.i("Poller", "Got " + queuedCommands.getAll().size() + " commands");
                    listener.onPollComplete(queuedCommands);
                }
                catch (Throwable t) {
                    t.printStackTrace();
                    Log.e("Poller", t.getMessage());
                }
            }
        }, 0, POLLER_FREQUENCY, TimeUnit.SECONDS);
    }

    public void stop () {
        scheduledFuture.cancel(true);
    }

    public interface Listener {
        void onPollComplete(Games lobbyGameList);
        void onPollComplete(Commands queuedCommands);
    }
}
