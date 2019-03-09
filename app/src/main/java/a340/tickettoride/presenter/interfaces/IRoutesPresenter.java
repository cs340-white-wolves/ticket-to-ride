package a340.tickettoride.presenter.interfaces;

import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;

import java.util.List;
import java.util.Set;

import cs340.TicketToRide.model.game.card.DestinationCard;

public interface IRoutesPresenter {
    DestinationCards getPlayerDestCards();

    List<DestinationCard> getAllRoutes();
    Set<DestinationCard> getCompletedRoutes();
}
