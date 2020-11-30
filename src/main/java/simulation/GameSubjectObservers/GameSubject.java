package simulation.GameSubjectObservers;

import org.apache.log4j.Logger;
import simulation.model.ILeague;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class GameSubject {
//Source: https://refactoring.guru/design-patterns/observer

    private static final Logger log = Logger.getLogger(GameSubject.class);
    private final List<IGameObserver> observers;

    public GameSubject() {
        observers = new ArrayList<IGameObserver>();
    }

    public void attach(IGameObserver observer) {
        observers.add(observer);
    }

    public void detach(IGameObserver observer) {
        observers.remove(observer);
    }

    public List<IGameObserver> getListeners() {
        return observers;
    }

    public void notifyObservers(ILeague league, String team, Integer count) {
        if (league == null || team == null) {
            log.error("league and team are null while notifying observers in Game Subject");
            throw new IllegalArgumentException("league and team are null while notifying observers in Game Subject");
        }
        ListIterator<IGameObserver> iter = observers.listIterator();
        while (iter.hasNext()) {
            iter.next().update(league, team, count);
        }
    }
}
