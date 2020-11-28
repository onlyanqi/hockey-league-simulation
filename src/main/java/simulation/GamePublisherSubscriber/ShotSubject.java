package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;

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

	public void attach(IShotSubscriber observer)
	{
		observers.add(observer);
	}

	public void detach(IShotSubscriber observer)
	{
		observers.remove(observer);
	}

	public void notifyObservers(ILeague league, String team, Integer count)
	{
		ListIterator<IShotSubscriber> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updateShot(league,team,count);
		}
	}
}
