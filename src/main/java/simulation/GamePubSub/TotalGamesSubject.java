package simulation.GamePubSub;

import simulation.model.ILeague;
import simulation.model.IPlayer;
import simulation.model.ITeam;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TotalGamesSubject
{
	private final List<ITotalGameSub> observers;
	private static TotalGamesSubject instance;

	public static TotalGamesSubject getInstance() {
		if (instance == null) {
			instance = new TotalGamesSubject();
		}
		return instance;
	}

	public TotalGamesSubject()
	{
			observers = new ArrayList<ITotalGameSub>();
	}

	// Subscribe an observer to the subject. The observer will be
	// notified when the subject is notified.
	public void attach(ITotalGameSub observer)
	{
		observers.add(observer);
	}

	// Cancel an observer's subscription to the subject. The observer
	// is no longer interested in being notified.
	public void detach(ITotalGameSub observer)
	{
		observers.remove(observer);
	}

	// Notify all observers of the subject that the subject
	// event has happened. I wanted this to be called notify() but
	// Java has the Object.notify() method as final.
	public void notifyObservers(ILeague league, String team, Integer numberOfGames)
	{
		ListIterator<ITotalGameSub> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updateTotalGames(league,team,numberOfGames);
		}
	}
}
