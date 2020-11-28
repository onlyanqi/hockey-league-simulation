package simulation.GameSubjectObservers;

import simulation.model.ILeague;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SaveSubject extends GameSubject {
	private static SaveSubject instance;

	public static SaveSubject getInstance() {
		if (instance == null) {
			instance = new SaveSubject();
		}
		return instance;
	}
}
