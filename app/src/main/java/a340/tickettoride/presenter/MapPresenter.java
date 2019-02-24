package a340.tickettoride.presenter;

import java.util.HashSet;
import java.util.Set;

import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import cs340.TicketToRide.model.game.board.City;

public class MapPresenter implements IMapPresenter, ModelObserver {
    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {

    }

    public Set<City> getActiveGameCities() {
        Set<City> cities = new HashSet<>();
        cities.add(new City("Salt Lake City", (float)40.7608, (float)-111.8910));
        return cities;
    }
}
