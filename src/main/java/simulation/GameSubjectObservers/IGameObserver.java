package simulation.GameSubjectObservers;

import simulation.model.ILeague;

public interface IGameObserver
{
	void update(ILeague league, String team, Integer count);
}