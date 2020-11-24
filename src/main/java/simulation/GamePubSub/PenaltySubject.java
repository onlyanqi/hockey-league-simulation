package simulation.GamePubSub;

import simulation.model.IGame;
import simulation.model.ILeague;
import simulation.model.IPlayer;
import simulation.model.ITeam;

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

	// Subscribe an observer to the subject. The observer will be
	// notified when the subject is notified.
	public void attach(IPenaltyObserver observer)
	{
		observers.add(observer);
	}

	// Cancel an observer's subscription to the subject. The observer
	// is no longer interested in being notified.
	public void detach(IPenaltyObserver observer)
	{
		observers.remove(observer);
	}

	// Notify all observers of the subject that the subject
	// event has happened. I wanted this to be called notify() but
	// Java has the Object.notify() method as final.
	public void notifyObservers(ILeague league, String team, Integer count)
	{
		ListIterator<IPenaltyObserver> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updatePenalty(league,team,count);
		}
	}
}
