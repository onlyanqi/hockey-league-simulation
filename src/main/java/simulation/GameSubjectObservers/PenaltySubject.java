package simulation.GameSubjectObservers;

import simulation.model.ILeague;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PenaltySubject extends GameSubject
{
	private static PenaltySubject instance;

	public static PenaltySubject getInstance() {
		if (instance == null) {
			instance = new PenaltySubject();
		}
		return instance;
	}
}
