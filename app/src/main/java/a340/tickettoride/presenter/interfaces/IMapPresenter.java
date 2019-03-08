package a340.tickettoride.presenter.interfaces;

import java.util.List;
import java.util.Set;

import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.utility.ID;

public interface IMapPresenter {
    List<Player> getPlayers();
    Player getPlayerById(ID playerId);
    List<Route> getPossibleRoutesToClaim();
    List<DestinationCard> getPlayerDestCards();
    void discardDestCards();
    void placeTrains();
//    Set<City> getActiveGameCities();
}
