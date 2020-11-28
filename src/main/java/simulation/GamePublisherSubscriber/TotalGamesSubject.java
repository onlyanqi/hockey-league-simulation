package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;
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

	public void attach(ITotalGameSub observer)
	{
		observers.add(observer);
	}

	public void detach(ITotalGameSub observer)
	{
		observers.remove(observer);
	}

	public void notifyObservers(ILeague league, String team, Integer numberOfGames)
	{
		ListIterator<ITotalGameSub> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updateTotalGames(league,team,numberOfGames);
		}
	}
}
