package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PenaltySubject
{
	private final List<IPenaltyObserver> observers;
	private static PenaltySubject instance;

	public static PenaltySubject getInstance() {
		if (instance == null) {
			instance = new PenaltySubject();
		}
		return instance;
	}

	public PenaltySubject()
	{
			observers = new ArrayList<IPenaltyObserver>();
	}

	public void attach(IPenaltyObserver observer)
	{
		observers.add(observer);
	}

	public void detach(IPenaltyObserver observer)
	{
		observers.remove(observer);
	}

	public void notifyObservers(ILeague league, String team, Integer count)
	{
		ListIterator<IPenaltyObserver> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updatePenalty(league,team,count);
		}
	}
}
