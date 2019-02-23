package a340.tickettoride.observerable;

import java.util.HashSet;
import java.util.Set;

public class ModelObservable {
    private Set<ModelObserver> observers;

    public ModelObservable() {
        observers = new HashSet<>();
    }

    public boolean addObserver(ModelObserver observer) {
        return observers.add(observer);
    }

    public boolean deleteObserver(ModelObserver observer) {
        return observers.remove(observer);
    }

    protected void notifyObservers(ModelChangeType changeType, Object obj) {
        for (ModelObserver observer : observers) {
            observer.onModelEvent(changeType, obj);
        }
    }
}
