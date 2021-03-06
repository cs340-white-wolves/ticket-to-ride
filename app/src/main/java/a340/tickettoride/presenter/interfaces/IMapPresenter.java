package a340.tickettoride.presenter.interfaces;

import java.util.List;
import java.util.Set;

import a340.tickettoride.model.ClientModel;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.utility.ID;

public interface IMapPresenter {
    void startObserving();
    void stopObserving();
    Players getPlayers();
    Player getPlayerById(ID playerId);
    Set<City> getCities();
    Set<Route> getRoutes();
    void onSetTurn(int playerIdx);
    void startPoller();
    void onClickDrawDestCards();
    boolean isActivePlayerTurn();
}
