package simulation.GameSubjectObservers;

import simulation.model.ILeague;
//Source: https://refactoring.guru/design-patterns/observer

public interface IGameObserver {
    void update(ILeague league, String team, Integer count);
}