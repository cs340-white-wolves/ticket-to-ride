package a340.tickettoride.task;

import android.os.AsyncTask;

import a340.tickettoride.ServerProxy;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IServer;
import cs340.TicketToRide.model.AuthToken;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.utility.ID;

public class DrawTrainCardTask extends AsyncTask<Void, Integer, Void> {
    private IClientModel model = ClientModel.getInstance();
    private Exception exception;
    private TrainCard card;
    private boolean advanceTurn;

    public DrawTrainCardTask(TrainCard card, boolean advanceTurn) {
        this.card = card;
        this.advanceTurn = advanceTurn;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        IServer server = ServerProxy.getInstance();
        AuthToken token = model.getAuthToken();
        ID gameId = model.getActiveGame().getGameID();
        ID playerId = model.getPlayerId();

        try {
            server.drawTrainCard(card, advanceTurn, token, gameId, playerId);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }
}
