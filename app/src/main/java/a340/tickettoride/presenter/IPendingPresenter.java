package a340.tickettoride.presenter;

import java.util.Set;

import cs340.TicketToRide.model.Player;

public interface IPendingPresenter {
    public String getGameName();
    public Set<Player> getPlayers();
}
