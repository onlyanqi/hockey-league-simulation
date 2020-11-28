package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SaveSubject
{
	private final List<ISaveSubscriber> observers;
	private static SaveSubject instance;

	public static SaveSubject getInstance() {
		if (instance == null) {
			instance = new SaveSubject();
		}
		return instance;
	}

	public SaveSubject()
	{
			observers = new ArrayList<ISaveSubscriber>();
	}

	public void attach(ISaveSubscriber observer)
	{
		observers.add(observer);
	}

	public void detach(ISaveSubscriber observer)
	{
		observers.remove(observer);
	}

	public void notifyObservers(ILeague league,  String team, Integer count)
	{
		ListIterator<ISaveSubscriber> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updateSave(league,team,count);
		}
	}
}
