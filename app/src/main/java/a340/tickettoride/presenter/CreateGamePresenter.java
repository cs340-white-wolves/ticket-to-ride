package a340.tickettoride.presenter;

import a340.tickettoride.task.CreateGameTask;
import cs340.TicketToRide.model.Game;

public class CreateGamePresenter implements ICreateGamePresenter {
    @Override
    public void createGame(int numPlayers) {
        if (numPlayers < Game.MIN_PLAYERS || numPlayers > Game.MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        CreateGameTask task = new CreateGameTask(numPlayers);
        task.execute();
    }
}
