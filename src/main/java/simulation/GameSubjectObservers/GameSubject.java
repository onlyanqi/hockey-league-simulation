package simulation.GameSubjectObservers;

import simulation.model.ILeague;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class GameSubject {
    private final List<IGameObserver> observers;

    public GameSubject()
    {
        observers = new ArrayList<IGameObserver>();
    }

    public void attach(IGameObserver observer)
    {
        observers.add(observer);
    }

    public void detach(IGameObserver observer)
    {
        observers.remove(observer);
    }

    public void notifyObservers(ILeague league, String team, Integer count)
    {
        ListIterator<IGameObserver> iter = observers.listIterator();
        while (iter.hasNext())
        {
            iter.next().update(league,team,count);
        }
    }
}
