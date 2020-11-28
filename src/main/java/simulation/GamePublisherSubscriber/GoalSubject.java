package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;
import java.util.*;

public class GoalSubject
{
	private final List<IGoalSubscriber> observers;
	private static GoalSubject instance;

	public static GoalSubject getInstance() {
		if (instance == null) {
			instance = new GoalSubject();
		}
		return instance;
	}

	public GoalSubject()
	{
			observers = new ArrayList<IGoalSubscriber>();
	}

	public void attach(IGoalSubscriber observer)
	{
		observers.add(observer);
	}

	public void detach(IGoalSubscriber observer)
	{
		observers.remove(observer);
	}

	public void notifyObservers(ILeague league, String team, Integer count)
	{
		ListIterator<IGoalSubscriber> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updateGoal(league,team,count);
		}
	}
}
