package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;

public interface ITotalGameSub {
    void updateTotalGames(ILeague league,String team,Integer count);
}
