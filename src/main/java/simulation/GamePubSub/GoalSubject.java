package simulation.GamePubSub;

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

	// Subscribe an observer to the subject. The observer will be
	// notified when the subject is notified.
	public void attach(IGoalSubscriber observer)
	{
		observers.add(observer);
	}

	// Cancel an observer's subscription to the subject. The observer
	// is no longer interested in being notified.
	public void detach(IGoalSubscriber observer)
	{
		observers.remove(observer);
	}

	// Notify all observers of the subject that the subject
	// event has happened. I wanted this to be called notify() but
	// Java has the Object.notify() method as final.
	public void notifyObservers(ILeague league, String team, Integer count)
	{
		ListIterator<IGoalSubscriber> iter = observers.listIterator();
		while (iter.hasNext())
		{
			iter.next().updateGoal(league,team,count);
		}
	}
}
