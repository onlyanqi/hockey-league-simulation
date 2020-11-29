package simulation.GameSubjectObservers;

import simulation.model.ILeague;
import java.util.*;

public class GoalSubject extends GameSubject
{
	private static GoalSubject instance;

	public static GoalSubject getInstance() {
		if (instance == null) {
			instance = new GoalSubject();
		}
		return instance;
	}

}
