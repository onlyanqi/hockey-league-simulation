package simulation.GamePubSub;

import simulation.model.IGame;
import simulation.model.ILeague;
import simulation.model.IPlayer;
import simulation.model.ITeam;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ShotSubject
{
	private final List<IShotSubscriber> observers;
	private static ShotSubject instance;

	public static ShotSubject getInstance() {
		if (instance == null) {
			instance = new ShotSubject();
		}
		return instance;
	}

	public ShotSubject()
	{
			observers = new ArrayList<IShotSubscriber>();
	}

	// Subscribe an observer to the subject. The observer will be
	// notified when the subject is notified.
	public void attach(IShotSubscriber observer)
	{
		observers.add(observer);
	}

	// Cancel an observer's subscription to the subject. The observer
	// is no longer interested in being notified.
	public void detach(IShotSubscriber observer)
	{
		observers.remove(observer);
	}

	// Notify all observers of the subject that the subject
	// event has happened. I wanted this to be called notify() but
	// Java has the Object.notify() method as final.
	public void notifyObservers(ILeague league, String team, Integer count)
	{
		ListIterator<IShotSubscriber> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updateShot(league,team,count);
		}
	}
}
