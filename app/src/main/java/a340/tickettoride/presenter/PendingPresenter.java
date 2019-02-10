package a340.tickettoride.presenter;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.model.IClientModel;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import a340.tickettoride.model.ClientModel;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.utility.ID;

public class PendingPresenter implements IPendingPresenter, Observer {
    private View view;
    private Game activeGame;

    public PendingPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO: right now, this is using the entire game list. We should switch to only polling the active game.
        if (arg instanceof Games) {
            Games games = (Games) arg;

            ClientModel model = ClientModel.getInstance();
            ID gameId = model.getActiveGame().getGameID();

            activeGame = games.getGameByID(gameId);

            view.onUpdatePlayers(activeGame.getPlayers());

            if (activeGame.hasTargetNumPlayers()) {
                view.onGameStarting();
            }
        }
    }

    @Override
    public Game getActiveGame() {
        return activeGame;
    }

    public interface View {
        void onUpdatePlayers(Set<Player> players);
        void onGameStarting();
    }
}
