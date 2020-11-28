package simulation.GameSubjectObservers;

import simulation.model.ILeague;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TotalGamesSubject extends GameSubject
{
	private static TotalGamesSubject instance;

	public static TotalGamesSubject getInstance() {
		if (instance == null) {
			instance = new TotalGamesSubject();
		}
		return instance;
	}

}
