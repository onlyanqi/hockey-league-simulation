package simulation.GameSubjectObservers;

import simulation.model.ILeague;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ShotSubject extends GameSubject
{
	private static ShotSubject instance;

	public static ShotSubject getInstance() {
		if (instance == null) {
			instance = new ShotSubject();
		}
		return instance;
	}


}
