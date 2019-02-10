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

    public PendingPresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public String getGameName() {
        // return the name of the active game

        return null;
    }

    @Override
    public Set<Player> getPlayers() {
        // return the set of players (usernames) in the active game

        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO: right now, this is using the entire game list. We should switch to only polling the active game.
        if (arg instanceof Games) {
            Games games = (Games) arg;

            ClientModel model = ClientModel.getInstance();
            ID gameId = model.getActiveGame().getGameID();

            Game game = games.getGameByID(gameId);

            view.onUpdatePlayers(game.getPlayers());

            if (game.hasTargetNumPlayers()) {
                view.onGameStarting();
            }
        }
    }

    public interface View {
        void onUpdatePlayers(Set<Player> players);
        void onGameStarting();
    }
}
