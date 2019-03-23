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
//    List<Route> getPossibleRoutesToClaim();
    DestinationCards getPlayerDestCards();
//    void discardDestCards();
//    void placeTrains();
    Set<City> getCities();
    Set<Route> getRoutes();
    void advanceTurn();
    void startPoller();
}
