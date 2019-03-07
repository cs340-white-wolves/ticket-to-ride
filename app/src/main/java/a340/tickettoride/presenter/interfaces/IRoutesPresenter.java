package a340.tickettoride.presenter.interfaces;

import java.util.List;
import java.util.Set;

import cs340.TicketToRide.model.game.card.DestinationCard;

public interface IRoutesPresenter {

    List<DestinationCard> getAllRoutes();
    Set<DestinationCard> getCompletedRoutes();
}
