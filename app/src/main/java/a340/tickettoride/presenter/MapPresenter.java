package a340.tickettoride.presenter;

import java.util.Set;

import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import cs340.TicketToRide.model.game.board.City;

public class MapPresenter implements IMapPresenter, ModelObserver {
    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {

    }

    public Set<City> getActiveGameCities() {
        return null;
    }
}
