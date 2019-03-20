package a340.tickettoride;

import android.util.Log;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.IClient;
import cs340.TicketToRide.model.game.Message;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCards;

public class ClientFacade implements IClient {
    private static ClientFacade singleton;
    private IClientModel model = ClientModel.getInstance();

    private ClientFacade() {
    }

    public static ClientFacade getInstance() {
        if (singleton == null) {
            singleton = new ClientFacade();
        }
        return singleton;
    }

    @Override
    public void chatMessageReceived(Message message) {
        model.onChatMessageReceived(message);
        Log.i("ClientFacade", "Got chat message!");
    }

    @Override
    public void historyMessageReceived(Message message) {
        model.onHistoryMessageReceived(message);
    }

    @Override
    public void playersUpdated(Players players) {
        model.updatePlayers(players);
    }

    @Override
    public void destCardDeckChanged(DestinationCards destCardDeck) {
        model.updateGameDestCardDeck(destCardDeck);
    }

    @Override
    public void startGame() {
        model.onGameStart();
    }

    @Override
    public void faceUpDeckChanged(TrainCards trainCards) {
        model.updateFaceUpTrainCards(trainCards);
    }

    @Override
    public void trainCardDeckChanged(TrainCards trainCards) {
        model.updateTrainCardDeck(trainCards);
    }

    @Override
    public void routeUpdated(Route route) {
        model.updateRoute(route);
    }

}
